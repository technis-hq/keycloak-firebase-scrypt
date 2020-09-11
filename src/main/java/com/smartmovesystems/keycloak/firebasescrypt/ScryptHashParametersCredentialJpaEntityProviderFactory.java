package com.smartmovesystems.keycloak.firebasescrypt;

import org.keycloak.Config;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class ScryptHashParametersCredentialJpaEntityProviderFactory implements JpaEntityProviderFactory {

    protected static final String ID = "scrypt-hash-parameters-credential-provider";

    @Override
    public JpaEntityProvider create(KeycloakSession session) {
        return new ScryptHashParametersCredentialJpaEntityProvider();
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int order() {
        return 0;
    }
}
