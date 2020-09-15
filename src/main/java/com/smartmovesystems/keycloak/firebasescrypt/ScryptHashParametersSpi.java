package com.smartmovesystems.keycloak.firebasescrypt;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class ScryptHashParametersSpi implements Spi {
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "firebase-scrypt";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return ScryptParametersProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return ScryptParametersProviderFactory.class;
    }
}
