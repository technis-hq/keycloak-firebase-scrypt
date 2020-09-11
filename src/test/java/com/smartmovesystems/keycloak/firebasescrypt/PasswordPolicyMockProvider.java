package com.smartmovesystems.keycloak.firebasescrypt;

import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.policy.PasswordPolicyProvider;
import org.keycloak.policy.PolicyError;

public class PasswordPolicyMockProvider implements PasswordPolicyProvider {
    @Override
    public PolicyError validate(RealmModel realm, UserModel user, String password) {
        return null;
    }

    @Override
    public PolicyError validate(String user, String password) {
        return null;
    }

    @Override
    public Object parseConfig(String value) {
        return value;
    }

    @Override
    public Integer parseInteger(String value, Integer defaultValue) {
        return null;
    }

    @Override
    public void close() {

    }
}
