package com.smartmovesystems.keycloak.firebasescrypt;

import com.lambdaworks.crypto.SCrypt;
import org.apache.commons.codec.binary.Base64;
import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.credential.PasswordCredentialModel;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides password hash verification for users imported from multiple different Firebase projects using Google's
 * customised Scrypt/AES password hashing algorithm
 */
public class ScryptPasswordHashProvider implements PasswordHashProvider {
    private final Logger logger = Logger.getLogger(ScryptPasswordHashProvider.class.getName());
    private final String providerId;
    private final ScryptParametersProvider parametersProvider;
    private static final Charset CHARSET = StandardCharsets.US_ASCII;
    private static final String CIPHER = "AES/CTR/NoPadding";
    private static final String DEFAULT_SALT_SEP = "Bw==";
    private  ScryptHashParametersRepresentation defaultParams;
    private final SaltProvider saltProvider;

    public ScryptPasswordHashProvider(String providerId, ScryptParametersProvider parametersProvider, SaltProvider saltProvider) {
        this.providerId = providerId;
        this.parametersProvider = parametersProvider;
        this.saltProvider = saltProvider;
    }

    @Override
    public boolean policyCheck(PasswordPolicy policy, PasswordCredentialModel credential) {
        return providerId.equals(credential.getPasswordCredentialData().getAlgorithm())
                && providerId.equals(policy.getHashAlgorithm());
    }

    @Override
    public PasswordCredentialModel encodedCredential(String rawPassword, int iterations) {

        byte[] salt = saltProvider.getSalt();

        try {
            ScryptHashParametersRepresentation defParams = getDefault();
            if (defParams == null) {
                logger.log(Level.SEVERE, "Cannot encode credential--no default hash parameters");
                return null;
            }
            String encodedPassword = encode(rawPassword, new String(Base64.encodeBase64(salt), CHARSET), defParams);
            if (defParams.id != null) {
                encodedPassword += "$" + defParams.id;
            }
            return PasswordCredentialModel.createFromValues(providerId, salt, iterations, encodedPassword);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error encoding credential", e);
            return null;
        }
    }

    private String encode(String rawPassword, String salt, ScryptHashParametersRepresentation parameters) throws GeneralSecurityException {
        byte[] rawEncrypted = getCipherText(
                rawPassword,
                salt,
                parameters.getSaltSeparator(),
                parameters.getBase64Signer(),
                parameters.getRounds(),
                parameters.getMemCost()
        );
        return new String(Base64.encodeBase64(rawEncrypted), CHARSET);
    }

    @Override
    public void close() {

    }

    /**
     * Attempt to get the correct scrypt hashing parameters for the credential entity
     * @param parametersId
     * @return The scrypt hashing parameters, or default if no associated parameters found
     */
    private ScryptHashParametersRepresentation getParametersForCredential(String parametersId) {
        ScryptHashParametersRepresentation result = null;
        if (parametersId != null) {
            result = parametersProvider.getHashParametersById(parametersId);
        }
        return result != null ? result : getDefault();
    }

    private ScryptHashParametersRepresentation getDefault() {
        try {
            if (defaultParams == null) {
                defaultParams = parametersProvider.getDefaultParameters();
            }
            return defaultParams;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error retrieving default hashing parameters, using fallback", e);
        }
        return fallbackDefault();
    }

    /**
     * If no default hashing parameters have been added yet, just use scrypt without pepper
     * @return
     */
    private ScryptHashParametersRepresentation fallbackDefault() {
        return new ScryptHashParametersRepresentation(null, 8, 14, null, DEFAULT_SALT_SEP, true);
    }

    @Override
    public boolean verify(String rawPassword, PasswordCredentialModel credential) {
        final String storedHash = credential.getPasswordSecretData().getValue();
        final String[] storedHashParts = storedHash.split("\\$");
        final String salt = new String(Base64.encodeBase64(credential.getPasswordSecretData().getSalt()));
        final String hashedPassword = storedHashParts[0];
        final String hashParametersId = storedHashParts.length > 1 ? storedHashParts[1] : null;
        boolean verified = false;
        try {
            ScryptHashParametersRepresentation parameters = getParametersForCredential(hashParametersId);
            if (parameters != null) {
                verified = check(rawPassword, hashedPassword, salt, parameters.getSaltSeparator(), parameters.getBase64Signer(), parameters.getRounds(), parameters.getMemCost());
            } else {
                logger.log(Level.SEVERE, "Cannot verify credential--no valid hash parameters");
            }
        } catch (GeneralSecurityException e) {
            logger.log(Level.SEVERE, "Error verifying credential", e);
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

        if (signer != null) {
            // encrypting with aes
            byte[] signerBytes = Base64.decodeBase64(signer.getBytes(CHARSET));
            return encrypt(signerBytes, hashedBytes);
        } else {
            // No signer configured, just use scrpyt hashing
            return hashedBytes;
        }
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
            byte[] iv = new byte[16];
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
