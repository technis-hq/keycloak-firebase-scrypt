# Keycloak Firebase Scrypt

Add a password hash provider to handle password hashing with the custom [Firebase Scrypt](https://github.com/firebase/scrypt) algorithm inside Keycloak.
Implementation adapted from [firebase-scrypt-java](https://github.com/SmartMoveSystems/firebase-scrypt-java) and [leroyguillaume/keycloak-bcrypt](https://github.com/leroyguillaume/keycloak-bcrypt).

## Build from source
```bash
./gradlew jar
```

## Download latest built version

```
curl -L https://github.com/SmartMoveSystems/keycloak-firebase-scrypt/releases/download/2.0.5/keycloak-firebase-scrypt-2.0.5.jar > KEYCLOAK_HOME/standalone/deployments/keycloak-firebase-scrypt-2.0.5.jar
```

## Run

## docker-compose

```bash
cp deploy.cli docker/
cp build/libs/keycloak-firebase-scrypt-2.0.5.jar docker/
docker-compose up -d
```

### Standalone

Deploy module:

```
$KEYCLOAK_HOME/bin/jboss-cli.sh --command="module add --name=com.smartmovesystems.keycloak.firebasescrypt --resources=build/libs/keycloak-firebase-scrypt-2.0.5.jar --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-services,org.keycloak.keycloak-model-jpa,org.keycloak.keycloak-server-spi,org.keycloak.keycloak-server-spi-private,javax.ws.rs.api,javax.persistence.api,org.hibernate,org.javassist,org.liquibase"
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

Use the [Firebase to Keycloak export/import tool](https://github.com/SmartMoveSystems/firebase-keycloak-importer) to get users from Firebase into Keycloak.