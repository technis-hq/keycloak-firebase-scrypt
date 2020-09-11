package com.smartmovesystems.keycloak.firebasescrypt;

import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersEntity;
import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersMappingEntity;

public interface ScryptParametersProvider {
    ScryptHashParametersMappingEntity getMappingEntityForCredentialId(String credentialId);
    ScryptHashParametersEntity getDefaultParameters();
}
