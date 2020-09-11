# Keycloak Firebase Scrypt

Add a password hash provider to handle password hashing with the custom [Firebase Scrypt](https://github.com/firebase/scrypt) algorithm inside Keycloak.
Implementation adapted from [firebase-scrypt-java](https://github.com/SmartMoveSystems/firebase-scrypt-java) and [leroyguillaume/keycloak-bcrypt](https://github.com/leroyguillaume/keycloak-bcrypt).

## Build
```bash
./gradlew jar
```

## Test with docker-compose
```bash
cp build/libs/keycloak-firebase-scrypt-0.0.1.jar docker/
docker-compose up -d
```

## Install
```
curl -L https://github.com/SmartMoveSystems/keycloak-firebase-scrypt/releases/download/0.0.1/keycloak-firebase-scrypt-1.5.0.jar > KEYCLOAK_HOME/standalone/deployments/keycloak-firebase-scrypt-0.0.1.jar
```
You need to restart Keycloak.

## How to use
Go to `Authentication` / `Password policy` and add hashing algorithm policy with value `firebase-scrypt`.

To test if installation works, create new user and set its credentials.
