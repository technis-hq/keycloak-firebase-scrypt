package com.smartmovesystems.keycloak.firebasescrypt;

import com.smartmovesystems.keycloak.firebasescrypt.model.ScryptHashParametersEntity;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

import java.util.Collections;
import java.util.List;

import static com.smartmovesystems.keycloak.firebasescrypt.ScryptPasswordHashProviderFactory.ID;

public class ScryptHashParametersJpaEntityProvider implements JpaEntityProvider {

    @Override
    public List<Class<?>> getEntities() {
        return Collections.singletonList(ScryptHashParametersEntity.class);
    }

    @Override
    public String getChangelogLocation() {
        return "META-INF/scrypt-hash-parameters-changelog.xml";
    }

    @Override
    public String getFactoryId() {
        return ID;
    }

    @Override
    public void close() {

    }
}
