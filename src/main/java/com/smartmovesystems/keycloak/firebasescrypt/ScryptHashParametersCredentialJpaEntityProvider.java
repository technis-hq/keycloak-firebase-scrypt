package com.smartmovesystems.keycloak.firebasescrypt;

import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersCredentialEntity;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

import java.util.Collections;
import java.util.List;


public class ScryptHashParametersCredentialJpaEntityProvider implements JpaEntityProvider {

    @Override
    public List<Class<?>> getEntities() {
        return Collections.singletonList(ScryptHashParametersCredentialEntity.class);
    }

    @Override
    public String getChangelogLocation() {
        return "META-INF/scrypt-hash-parameters-credential-changelog.xml";
    }

    @Override
    public String getFactoryId() {
        return ScryptHashParametersCredentialJpaEntityProviderFactory.ID;
    }

    @Override
    public void close() {

    }
}
