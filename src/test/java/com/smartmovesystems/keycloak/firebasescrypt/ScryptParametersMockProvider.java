package com.smartmovesystems.keycloak.firebasescrypt;

import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersEntity;
import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersCredentialEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ScryptParametersMockProvider implements ScryptParametersProvider {

    private List<ScryptHashParametersEntity> parametersEntityList = new ArrayList<>();

    public void add(ScryptHashParametersEntity scryptHashParameters) {
        parametersEntityList.add(scryptHashParameters);
    }


    @Override
    public ScryptHashParametersCredentialEntity getMappingEntityForCredentialId(String credentialId) {
        // There should only be one parameter mapping per credential Id
        return parametersEntityList
                .stream()
                .map(ScryptHashParametersEntity::getCredentialMappings)
                .flatMap(Collection::stream)
                .filter(mapping -> mapping.getCredentialId().equals(credentialId))
                .findFirst().orElse(null);
    }

    @Override
    public ScryptHashParametersEntity getDefaultParameters() {
        return parametersEntityList
                .stream()
                .filter(ScryptHashParametersEntity::isDefault)
                .findFirst()
                .orElse(null);
    }
}
