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

## Importing users and hashing parameters:

Use the [Firebase to Keycloak export/import tool](https://github.com/SmartMoveSystems/firebase-keycloak-importer) to get users and hash parameters from Firebase into Keycloak.

## Multiple Firebase projects

Importing users from multiple Firebase projects with different hash parameters is supported. All new users created after user import will have their passwords hashed using the hash parameters defined as the [default](https://github.com/SmartMoveSystems/firebase-keycloak-importer#usage).
If no hash parameter set is defined as the default, new users' passwords will be hashed using Scrypt only, as opposed to the custom [firebase-scrypt-java](https://github.com/SmartMoveSystems/firebase-scrypt-java) implementation.

## Releases

Releases are handled by GitHub Actions when a new tag is pushed to origin.
