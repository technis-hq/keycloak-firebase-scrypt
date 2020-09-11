package com.smartmovesystems.keycloak.firebasescrypt;

import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersEntity;
import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersMappingEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ScryptParametersEntityProvider implements ScryptParametersProvider {

    private final EntityManager entityManager;

    public ScryptParametersEntityProvider(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public ScryptHashParametersMappingEntity getMappingEntityForCredentialId(String credentialId) {
        TypedQuery<ScryptHashParametersMappingEntity> query = entityManager.createNamedQuery("findForCredential", ScryptHashParametersMappingEntity.class);
        query.setParameter("credentialId", credentialId);
        return query.getSingleResult();
    }

    @Override
    public ScryptHashParametersEntity getDefaultParameters() {
        return entityManager.createNamedQuery("findDefault", ScryptHashParametersEntity.class).getSingleResult();
    }
}
