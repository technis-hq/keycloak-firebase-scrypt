package com.smartmovesystems.keycloak.firebasescrypt.impl;

import com.smartmovesystems.keycloak.firebasescrypt.ScryptParametersProvider;
import com.smartmovesystems.keycloak.firebasescrypt.ScryptParametersProviderFactory;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderFactory;

public class ScryptParametersProviderFactoryImpl implements ScryptParametersProviderFactory {

    private static final String ID = "scrypt-parameters-provider-impl";

    @Override
    public ScryptParametersProvider create(KeycloakSession session) {
        return new ScryptParametersProviderImpl(session);
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
