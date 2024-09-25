#FROM jboss/keycloak:12.0.4
#FROM quay.io/keycloak/keycloak:12.0.4
FROM quay.io/tsystems/jboss/keycloak:12.0.4
COPY ./keycloak-firebase-scrypt-3.0.3.jar /opt/keycloak/providers/

#COPY ./themes/ /opt/jboss/keycloak/standalone/deployments/
COPY ./startup-scripts/ /opt/jboss/startup-scripts/
