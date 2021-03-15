# Keycloak Firebase Scrypt

Add a password hash provider to handle password hashing with the custom [Firebase Scrypt](https://github.com/firebase/scrypt) algorithm inside Keycloak.
Implementation adapted from [firebase-scrypt-java](https://github.com/SmartMoveSystems/firebase-scrypt-java) and [leroyguillaume/keycloak-bcrypt](https://github.com/leroyguillaume/keycloak-bcrypt).

## Build from source
```bash
./gradlew jar
```

## Download latest built version

```
curl -L https://github.com/SmartMoveSystems/keycloak-firebase-scrypt/releases/download/3.0.3/keycloak-firebase-scrypt-3.0.3.jar > KEYCLOAK_HOME/standalone/deployments/keycloak-firebase-scrypt-3.0.3.jar
```

## Run

## docker-compose

```bash
cp deploy.cli docker/
cp build/libs/keycloak-firebase-scrypt-3.0.3.jar docker/
docker-compose up -d
```

### Standalone

Deploy module:

```
$KEYCLOAK_HOME/bin/jboss-cli.sh --command="module add --name=com.smartmovesystems.keycloak.firebasescrypt --resources=build/libs/keycloak-firebase-scrypt-3.0.3.jar --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-services,org.keycloak.keycloak-model-jpa,org.keycloak.keycloak-server-spi,org.keycloak.keycloak-server-spi-private,javax.ws.rs.api,javax.persistence.api,org.hibernate,org.javassist,org.liquibase"
```

Register provider in `standalone/configuration/standalone.xml`:

```
<providers>
    ...
    <provider>module:com.smartmovesystems.keycloak.firebasescrypt</provider>
</providers>
```

Run `$KEYCLOAK_HOME/bin/standalone.sh`

## How to use
Go to `Authentication` / `Password policy` and add hashing algorithm policy with value `firebase-scrypt`.

## Importing users and hashing parameters:

Use the [Firebase to Keycloak export/import tool](https://github.com/SmartMoveSystems/firebase-keycloak-importer) to get users and hash parameters from Firebase into Keycloak.

## Multiple Firebase projects

Importing users from multiple Firebase projects with different hash parameters is supported. All new users created after user import will have their passwords hashed using the hash parameters defined as the [default](https://github.com/SmartMoveSystems/firebase-keycloak-importer#usage). If no hash parameter set is defined as the default, new users' passwords will be hashed using Scrypt only, as opposed to the custom [firebase-scrypt-java](https://github.com/SmartMoveSystems/firebase-scrypt-java) implementation.

## Releases

Releases are handled by GitHub Actions when a new tag is pushed to origin.