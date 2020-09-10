package com.smartmovesystems.keycloak.firebasescrypt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "SCRYPT_PARAMS")
@NamedQueries({
    @NamedQuery(name = "findDefault", query = "SELECT s FROM ScryptHashParametersEntity s WHERE s.isDefault = TRUE")
})
public class ScryptHashParametersEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;

    @Column(name = "ROUNDS")
    private int rounds;

    @Column(name = "MEM_COST")
    private int memCost;

    @Column(name = "SIGNER", length = 128)
    private String baser64Signer;

    @Column(name = "SALT_SEP", length = 64)
    private String saltSeparator;

    @Column(name = "IS_DEFAULT")
    private boolean isDefault;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "scryptParamsId")
    private final Collection<ScryptHashParametersMappingEntity> credentialMappings = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getMemCost() {
        return memCost;
    }

    public void setMemCost(int memCost) {
        this.memCost = memCost;
    }

    public String getBaser64Signer() {
        return baser64Signer;
    }

    public void setBaser64Signer(String baser64Signer) {
        this.baser64Signer = baser64Signer;
    }

    public String getSaltSeparator() {
        return saltSeparator;
    }

    public void setSaltSeparator(String saltSeparator) {
        this.saltSeparator = saltSeparator;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Collection<ScryptHashParametersMappingEntity> getCredentialMappings() {
        return credentialMappings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScryptHashParametersMappingEntity that = (ScryptHashParametersMappingEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
