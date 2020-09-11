package com.smartmovesystems.keycloak.firebasescrypt;

import java.security.SecureRandom;

public class SecureSaltProvider implements SaltProvider {

    /**
     * Generates a random 11-byte salt, consistent with Firebase
     * @return
     */
    @Override
    public byte[] getSalt() {
        byte[] out = new byte[11];
        new SecureRandom().nextBytes(out);
        return out;
    }
}
