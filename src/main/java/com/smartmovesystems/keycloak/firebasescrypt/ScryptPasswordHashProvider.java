package com.smartmovesystems.keycloak.firebasescrypt;

import com.lambdaworks.crypto.SCrypt;
import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersEntity;
import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersMappingEntity;
import org.apache.commons.codec.binary.Base64;
import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.credential.PasswordCredentialModel;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Provides password hash verification for users imported from multiple different Firebase projects using Google's
 * customised Scrypt/AES password hashing algorithm
 */
public class ScryptPasswordHashProvider implements PasswordHashProvider {
    private final String providerId;
    private EntityManager entityManager;
    private static final Charset CHARSET = StandardCharsets.US_ASCII;
    private static final String CIPHER = "AES/CTR/NoPadding";
    private final ScryptHashParametersEntity defaultParams;

    public ScryptPasswordHashProvider(String providerId, EntityManager entityManager) {
        this.providerId = providerId;
        this.entityManager = entityManager;
        defaultParams = entityManager.createNamedQuery("findDefault", ScryptHashParametersEntity.class).getSingleResult();
    }

    @Override
    public boolean policyCheck(PasswordPolicy policy, PasswordCredentialModel credential) {
        return providerId.equals(credential.getPasswordCredentialData().getAlgorithm());
    }

    @Override
    public PasswordCredentialModel encodedCredential(String rawPassword, int iterations) {

        byte[] salt = getSalt();

        try {
            String encodedPassword = encode(rawPassword, new String(Base64.encodeBase64(salt), CHARSET), defaultParams);
            return PasswordCredentialModel.createFromValues(providerId, salt, iterations, encodedPassword);
        } catch (GeneralSecurityException e) {
            return null;
        }
    }

    public String encode(String rawPassword, String salt, ScryptHashParametersEntity parameters) throws GeneralSecurityException {
        byte[] rawEncrypted = getCipherText(
                rawPassword,
                salt,
                parameters.getSaltSeparator(),
                parameters.getBaser64Signer(),
                parameters.getRounds(),
                parameters.getMemCost()
        );
        return new String(Base64.encodeBase64(rawEncrypted), CHARSET);
    }

    /**
     * Generates a random 11-byte salt, consistent with Firebase
     * @return
     */
    private byte[] getSalt() {
        byte[] out = new byte[11];
        new SecureRandom().nextBytes(out);
        return out;
    }

    @Override
    public void close() {

    }

    /**
     * Attempt to get the correct scrypt hashing parameters for the credential entity
     * @param credential
     * @return The scrypt hashing parameters, or default if no associated parameters found
     */
    private ScryptHashParametersEntity getParametersForCredential(PasswordCredentialModel credential) {
        TypedQuery<ScryptHashParametersMappingEntity> query = entityManager.createNamedQuery("findForCredential", ScryptHashParametersMappingEntity.class);
        query.setParameter("credentialId", credential.getId());
        ScryptHashParametersMappingEntity result = query.getSingleResult();
        return result != null ? result.getHashParametersEntity() : defaultParams;
    }

    @Override
    public boolean verify(String rawPassword, PasswordCredentialModel credential) {
        final String hash = credential.getPasswordSecretData().getValue();
        final String salt = new String(credential.getPasswordSecretData().getSalt());
        ScryptHashParametersEntity parameters = getParametersForCredential(credential);
        boolean verified;
        try {
            verified = check(rawPassword, hash, salt, "", parameters.getBaser64Signer(), parameters.getRounds(), parameters.getMemCost());
        } catch (GeneralSecurityException e) {
            verified = false;
        }
        return verified;
    }

    /**
     * Check if the password hashes to the known ciphertext
     * @param passwd the user's password
     * @param knownCipherText the known password hash after encryption
     * @param salt the salt
     * @param saltSep the salt separator
     * @param signer base64 signer key from firebase
     * @param rounds rounds scrypt parameter
     * @param memcost memcost scrypt parameter
     * @return
     * @throws GeneralSecurityException
     */
    private boolean check(String passwd, String knownCipherText, String salt, String saltSep, String signer, int rounds, int memcost) throws GeneralSecurityException {
        byte[] cipherTextBytes = getCipherText(passwd, salt, saltSep, signer, rounds, memcost);

        byte[] knownCipherTextBytes = Base64.decodeBase64(knownCipherText.getBytes(CHARSET));

        return Arrays.equals(knownCipherTextBytes, cipherTextBytes);
    }

    private byte[] getCipherText(String passwd, String salt, String saltSep, String signer, int rounds, int memcost) throws GeneralSecurityException {
        // hashing password
        byte[] hashedBytes = hashWithSalt(passwd, salt, saltSep, rounds, memcost);

        // encrypting with aes
        byte[] signerBytes = Base64.decodeBase64(signer.getBytes(CHARSET));
        return encrypt(signerBytes, hashedBytes);
    }

    /**
     * Encrypt using AES-CTR with zero nonce
     * @param signer Secret key from ScryptHashParametersEntity
     * @param derivedKey The key derived from the user's password using Scrypt
     * @return Encrypted byte buffer
     */
    private byte[] encrypt(byte[] signer, byte[] derivedKey) {
        try {
            Key key = generateKeyFromString(derivedKey);
            byte[] nonce = ByteBuffer.allocate(8).putLong(0).array();
            byte[] iv = new byte[16];
            System.arraycopy(nonce, 0, iv, 0, nonce.length);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher c = Cipher.getInstance(CIPHER);
            c.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            return c.doFinal(signer);
        } catch(Exception ex) {
            return null;
        }
    }

    private Key generateKeyFromString(byte[] keyVal) {
        return new SecretKeySpec(keyVal, 0, 32, "AES");
    }

    /**
     * Generates the scrypt hash of the user's password using the specified parameters
     * @param passwd
     * @param salt
     * @param saltSep
     * @param rounds
     * @param memcost
     * @return Hashed byte array
     * @throws GeneralSecurityException
     */
    private byte[] hashWithSalt(String passwd, String salt, String saltSep, int rounds, int memcost) throws GeneralSecurityException {
        int N = 1 << memcost;
        int p = 1;
        // concatenating decoded salt + separator
        byte[] decodedSaltBytes = Base64.decodeBase64(salt.getBytes(CHARSET));

        byte[] decodedSaltSepBytes = Base64.decodeBase64(saltSep.getBytes(CHARSET));

        byte[] saltConcat = new byte[decodedSaltBytes.length + decodedSaltSepBytes.length];
        System.arraycopy(decodedSaltBytes, 0, saltConcat, 0, decodedSaltBytes.length);
        System.arraycopy(decodedSaltSepBytes, 0, saltConcat, decodedSaltBytes.length, decodedSaltSepBytes.length);

        // hashing password
        return SCrypt.scrypt(passwd.getBytes(CHARSET), saltConcat, N, rounds, p, 64);
    }
}
