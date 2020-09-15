package com.smartmovesystems.keycloak.firebasescrypt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.keycloak.models.credential.dto.PasswordCredentialData;

public class ScryptPasswordCredentialData extends PasswordCredentialData {

    private String hashParametersId;

    public ScryptPasswordCredentialData(@JsonProperty("hashIterations") int hashIterations, @JsonProperty("algorithm") String algorithm) {
        this(hashIterations, algorithm, null);
    }

    @JsonCreator
    public ScryptPasswordCredentialData(
            @JsonProperty("hashIterations") int hashIterations,
            @JsonProperty("algorithm") String algorithm,
            @JsonProperty("hashParametersId") String hashParametersId) {
        super(hashIterations, algorithm);
        this.hashParametersId = hashParametersId;
    }

    public String getHashParametersId() {
        return hashParametersId;
    }
}
