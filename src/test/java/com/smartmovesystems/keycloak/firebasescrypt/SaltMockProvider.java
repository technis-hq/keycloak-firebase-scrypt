package com.smartmovesystems.keycloak.firebasescrypt;

import org.apache.commons.codec.binary.Base64;

public class SaltMockProvider implements SaltProvider {

    private String saltBase64 = "42xEC+ixf3L2lw==";

    @Override
    public byte[] getSalt() {
        return Base64.decodeBase64(saltBase64);
    }

    public void setSaltBase64(String saltBase64) {
        this.saltBase64 = saltBase64;
    }
}
