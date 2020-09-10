package com.smartmovesystems.keycloak.firebasescrypt;

import org.keycloak.Config;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.credential.hash.PasswordHashProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class ScryptPasswordHashProviderFactory implements PasswordHashProviderFactory {
    public static final String ID = "firebase-scrypt";

    @Override
    public PasswordHashProvider create(KeycloakSession session) {

        return new ScryptPasswordHashProvider(ID, session.getProvider(JpaConnectionProvider.class).getEntityManager());
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void close() {
    }
}
