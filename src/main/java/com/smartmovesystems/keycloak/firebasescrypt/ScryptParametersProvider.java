package com.smartmovesystems.keycloak.firebasescrypt;

import org.keycloak.provider.Provider;

import javax.persistence.NoResultException;
import java.util.List;

public interface ScryptParametersProvider extends Provider {
    ScryptHashParametersRepresentation addParameters(ScryptHashParametersRepresentation rep);
    ScryptHashParametersRepresentation getHashParametersById(String parametersId);
    ScryptHashParametersRepresentation getDefaultParameters() throws NoResultException;
    List<ScryptHashParametersRepresentation> getAllParameters();
}
