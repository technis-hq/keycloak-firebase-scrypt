package com.smartmovesystems.keycloak.firebasescrypt;

import com.smartmovesystems.keycloak.firebasescrypt.jpa.ScryptHashParametersEntity;

public class ScryptHashParametersRepresentation {

    public ScryptHashParametersRepresentation() {
    }

    public ScryptHashParametersRepresentation(ScryptHashParametersEntity entity) {
        id = entity.getId();
        rounds = entity.getRounds();
        memCost = entity.getMemCost();
        base64Signer = entity.getBase64Signer();
        saltSeparator = entity.getSaltSeparator();
        isDefault = entity.isDefault();
    }

    protected String id;

    private int rounds;

    private int memCost;

    private String base64Signer;

    private String saltSeparator;

    private boolean isDefault;

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

    public String getBase64Signer() {
        return base64Signer;
    }

    public void setBase64Signer(String base64Signer) {
        this.base64Signer = base64Signer;
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

    @Override
    public String toString() {
        return "ScryptHashParametersRepresentation{" +
                "id='" + id + '\'' +
                ", rounds=" + rounds +
                ", memCost=" + memCost +
                ", baser64Signer='" + base64Signer + '\'' +
                ", saltSeparator='" + saltSeparator + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
