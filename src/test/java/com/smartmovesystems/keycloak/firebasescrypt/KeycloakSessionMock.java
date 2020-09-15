package com.smartmovesystems.keycloak.firebasescrypt;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.models.cache.UserCache;
import org.keycloak.policy.PasswordPolicyProvider;
import org.keycloak.provider.Provider;
import org.keycloak.services.clientpolicy.ClientPolicyManager;
import org.keycloak.sessions.AuthenticationSessionProvider;
import org.keycloak.storage.federated.UserFederatedStorageProvider;
import org.keycloak.vault.VaultTranscriber;

import java.util.Set;

public class KeycloakSessionMock implements KeycloakSession {
    @Override
    public KeycloakContext getContext() {
        return null;
    }

    @Override
    public KeycloakTransactionManager getTransactionManager() {
        return null;
    }

    @Override
    public <T extends Provider> T getProvider(Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends Provider> T getProvider(Class<T> clazz, String id) {
        if (clazz == PasswordPolicyProvider.class) {
            return (T) new PasswordPolicyMockProvider();
        }
        return null;
    }

    @Override
    public <T extends Provider> T getProvider(Class<T> clazz, ComponentModel componentModel) {
        return null;
    }

    @Override
    public <T extends Provider> Set<String> listProviderIds(Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends Provider> Set<T> getAllProviders(Class<T> clazz) {
        return null;
    }

    @Override
    public Class<? extends Provider> getProviderClass(String providerClassName) {
        return null;
    }

    @Override
    public Object getAttribute(String attribute) {
        return null;
    }

    @Override
    public <T> T getAttribute(String attribute, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getAttributeOrDefault(String attribute, T defaultValue) {
        return null;
    }

    @Override
    public Object removeAttribute(String attribute) {
        return null;
    }

    @Override
    public void setAttribute(String name, Object value) {

    }

    @Override
    public void enlistForClose(Provider provider) {

    }

    @Override
    public KeycloakSessionFactory getKeycloakSessionFactory() {
        return null;
    }

    @Override
    public RealmProvider realms() {
        return null;
    }

    @Override
    public ClientProvider clients() {
        return null;
    }

    @Override
    public UserSessionProvider sessions() {
        return null;
    }

    @Override
    public AuthenticationSessionProvider authenticationSessions() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public UserCache userCache() {
        return null;
    }

    @Override
    public UserProvider users() {
        return null;
    }

    @Override
    public ClientProvider clientStorageManager() {
        return null;
    }

    @Override
    public UserProvider userStorageManager() {
        return null;
    }

    @Override
    public UserCredentialManager userCredentialManager() {
        return null;
    }

    @Override
    public UserProvider userLocalStorage() {
        return null;
    }

    @Override
    public RealmProvider realmLocalStorage() {
        return null;
    }

    @Override
    public ClientProvider clientLocalStorage() {
        return null;
    }

    @Override
    public UserFederatedStorageProvider userFederatedStorage() {
        return null;
    }

    @Override
    public KeyManager keys() {
        return null;
    }

    @Override
    public ThemeManager theme() {
        return null;
    }

    @Override
    public TokenManager tokens() {
        return null;
    }

    @Override
    public VaultTranscriber vault() {
        return null;
    }

    @Override
    public ClientPolicyManager clientPolicy() {
        return null;
    }
}
