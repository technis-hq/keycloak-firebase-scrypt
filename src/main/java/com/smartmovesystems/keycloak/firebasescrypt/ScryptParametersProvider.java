package com.smartmovesystems.keycloak.firebasescrypt;

import org.keycloak.provider.Provider;

import java.util.List;

public interface ScryptParametersProvider extends Provider {
    ScryptHashParametersRepresentation addParameters(ScryptHashParametersRepresentation rep);
    ScryptHashParametersRepresentation getHashParametersById(String parametersId);
    ScryptHashParametersRepresentation getDefaultParameters();
    List<ScryptHashParametersRepresentation> getAllParameters();
}
