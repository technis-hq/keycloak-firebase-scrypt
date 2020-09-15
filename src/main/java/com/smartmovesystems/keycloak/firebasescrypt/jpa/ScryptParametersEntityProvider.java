package com.smartmovesystems.keycloak.firebasescrypt;

import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ScryptParametersEntityProvider implements ScryptParametersProvider {

    private final EntityManager entityManager;

    public ScryptParametersEntityProvider(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public ScryptHashParametersEntity getHashParametersById(String parametersId) {
        TypedQuery<ScryptHashParametersEntity> query = entityManager.createNamedQuery("findById", ScryptHashParametersEntity.class);
        query.setParameter("id", parametersId);
        return query.getSingleResult();
    }

    @Override
    public ScryptHashParametersEntity getDefaultParameters() {
        return entityManager.createNamedQuery("findDefault", ScryptHashParametersEntity.class).getSingleResult();
    }
}
