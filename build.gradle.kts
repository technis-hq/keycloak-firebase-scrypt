plugins {
    java
}

group = "com.smartmovesystems.keycloak.firebasescrypt"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    val scryptVersion = "1.4.0"
    val commonsCodecVersion = "1.4"
    val jbossLoggingVersion = "3.4.1.Final"
    val keycloakVersion = "10.0.1"

    // Scrypt
    implementation("com.lambdaworks:scrypt:$scryptVersion")

    // Encoding
    implementation("commons-codec:commons-codec:$commonsCodecVersion")

    // JBoss
    compileOnly("org.jboss.logging:jboss-logging:$jbossLoggingVersion")

    // Keycloak
    compileOnly("org.keycloak:keycloak-common:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-core:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-server-spi:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-model-jpa:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-server-spi-private:$keycloakVersion")
}

tasks {
    jar {
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
            exclude("META-INF/MANIFEST.MF")
            exclude("META-INF/*.SF")
            exclude("META-INF/*.DSA")
            exclude("META-INF/*.RSA")
        }
    }

    wrapper {
        gradleVersion = "6.4"
    }
}
