FROM jboss/keycloak:11.0.2

COPY ./keycloak-firebase-scrypt-3.0.2.jar /opt/jboss/keycloak/
COPY ./themes/ /opt/jboss/keycloak/standalone/deployments/
COPY ./startup-scripts/ /opt/jboss/startup-scripts/
