package com.smartmovesystems.keycloak.firebasescrypt;

import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersEntity;
import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersCredentialEntity;

public interface ScryptParametersProvider {
    ScryptHashParametersCredentialEntity getMappingEntityForCredentialId(String credentialId);
    ScryptHashParametersEntity getDefaultParameters();
}
