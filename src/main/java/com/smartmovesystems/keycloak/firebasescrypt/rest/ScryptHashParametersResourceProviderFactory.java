package com.smartmovesystems.keycloak.firebasescrypt.rest;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class ScryptHashParametersResourceProviderFactory implements RealmResourceProviderFactory {

    public static final String ID = "scrypt";

    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new ScryptHashParametersResourceProvider(session);
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
