plugins { java }

group = "com.smartmovesystems.keycloak.firebasescrypt"

version = if (project.hasProperty("newVersion")) project.property("newVersion") as String else "latest"

repositories { mavenCentral() }

dependencies {
    val scryptVersion = "1.4.0"
    val commonsCodecVersion = "1.4"
    val jbossLoggingVersion = "3.4.1.Final"
    val keycloakVersion = "26.0.8"
    val junitVersion = "5.8.2"
    // Scrypt
    implementation("com.lambdaworks:scrypt:$scryptVersion")

    // Encoding
    implementation("commons-codec:commons-codec:$commonsCodecVersion")

    // JBoss
    compileOnly("org.jboss.logging:jboss-logging:$jbossLoggingVersion")
    testImplementation("org.jboss.logging:jboss-logging:$jbossLoggingVersion")

    // Keycloak
    compileOnly("org.keycloak:keycloak-common:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-core:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-server-spi:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-model-jpa:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-server-spi-private:$keycloakVersion")
    testImplementation("org.keycloak:keycloak-common:$keycloakVersion")
    testImplementation("org.keycloak:keycloak-core:$keycloakVersion")
    testImplementation("org.keycloak:keycloak-server-spi:$keycloakVersion")
    testImplementation("org.keycloak:keycloak-model-jpa:$keycloakVersion")
    testImplementation("org.keycloak:keycloak-server-spi-private:$keycloakVersion")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")



}

tasks {
     java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
     }


    jar {
        from(
                configurations.runtimeClasspath.get().map {
                    if (it.isDirectory) it else zipTree(it)
                }
        ) {
            exclude("META-INF/MANIFEST.MF")
            exclude("META-INF/*.SF")
            exclude("META-INF/*.DSA")
            exclude("META-INF/*.RSA")
        }
    }

    wrapper { gradleVersion = "7.6" }

}
