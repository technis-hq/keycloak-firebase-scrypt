package com.smartmovesystems.keycloak.firebasescrypt.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class ScryptHashParametersResourceProvider implements RealmResourceProvider {

    private KeycloakSession session;

    public ScryptHashParametersResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new ScryptHashParametersResource(session);
    }

    @Override
    public void close() {
    }
}
