package com.smartmovesystems.keycloak.firebasescrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScryptParametersMockProvider implements ScryptParametersProvider {

    private List<ScryptHashParametersRepresentation> parametersEntityList = new ArrayList<>();

    public void add(ScryptHashParametersRepresentation scryptHashParameters) {
        parametersEntityList.add(scryptHashParameters);
    }

    @Override
    public ScryptHashParametersRepresentation addParameters(ScryptHashParametersRepresentation rep) {
        rep.setId(UUID.randomUUID().toString());
        parametersEntityList.add(rep);
        return rep;
    }

    @Override
    public ScryptHashParametersRepresentation getHashParametersById(String parametersId) {
        return parametersEntityList
                .stream()
                .filter(params -> parametersId.equals(params.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ScryptHashParametersRepresentation getDefaultParameters() {
        return parametersEntityList
                .stream()
                .filter(ScryptHashParametersRepresentation::isDefault)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ScryptHashParametersRepresentation> getAllParameters() {
        return parametersEntityList;
    }

    @Override
    public void close() {

    }
}
