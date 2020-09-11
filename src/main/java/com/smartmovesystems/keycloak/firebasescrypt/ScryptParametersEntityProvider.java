package com.smartmovesystems.keycloak.firebasescrypt;

import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersEntity;
import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersCredentialEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ScryptParametersEntityProvider implements ScryptParametersProvider {

    private final EntityManager entityManager;

    public ScryptParametersEntityProvider(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public ScryptHashParametersCredentialEntity getMappingEntityForCredentialId(String credentialId) {
        TypedQuery<ScryptHashParametersCredentialEntity> query = entityManager.createNamedQuery("findForCredential", ScryptHashParametersCredentialEntity.class);
        query.setParameter("credentialId", credentialId);
        return query.getSingleResult();
    }

    @Override
    public ScryptHashParametersEntity getDefaultParameters() {
        return entityManager.createNamedQuery("findDefault", ScryptHashParametersEntity.class).getSingleResult();
    }
}
