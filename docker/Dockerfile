FROM jboss/keycloak:11.0.2

COPY ./keycloak-firebase-scrypt-2.0.6.jar /opt/jboss/keycloak/
COPY ./themes/ /opt/jboss/keycloak/standalone/deployments/
COPY ./startup-scripts/ /opt/jboss/startup-scripts/
