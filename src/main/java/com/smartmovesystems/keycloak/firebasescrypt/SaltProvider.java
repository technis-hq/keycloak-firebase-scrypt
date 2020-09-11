package com.smartmovesystems.keycloak.firebasescrypt;

public interface SaltProvider {
    byte[] getSalt();
}
