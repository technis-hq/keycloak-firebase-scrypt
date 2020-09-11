package com.smartmovesystems.keycloak.firebasescrypt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SCRYPT_PARAMS_CREDENTIAL")
@NamedQueries({
    @NamedQuery(name = "findForCredential", query = "SELECT s FROM ScryptHashParametersCredentialEntity s WHERE s.credentialId = :credentialId"),
})
public class ScryptHashParametersCredentialEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;

    @Column(name = "CREDENTIAL_ID", length = 36)
    private String credentialId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ScryptHashParametersEntity hashParametersEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public ScryptHashParametersEntity getHashParametersEntity() {
        return hashParametersEntity;
    }

    public void setHashParametersEntity(ScryptHashParametersEntity hashParametersEntity) {
        this.hashParametersEntity = hashParametersEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScryptHashParametersCredentialEntity that = (ScryptHashParametersCredentialEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
