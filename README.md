# Keycloak Firebase Scrypt

Add a password hash provider to handle password hashing with the custom [Firebase Scrypt](https://github.com/firebase/scrypt) algorithm inside Keycloak.
Implementation adapted from [firebase-scrypt-java](https://github.com/SmartMoveSystems/firebase-scrypt-java) and [leroyguillaume/keycloak-bcrypt](https://github.com/leroyguillaume/keycloak-bcrypt).

## Build from source

```bash
./gradlew jar
```

## Run

## docker-compose

```bash
docker compose up -d
```

## How to use

Go to `Authentication` / `Password policy` and add hashing algorithm policy with value `firebase-scrypt`.

## Releases

Releases are handled by GitHub Actions when a new tag is pushed to origin.
