package com.smartmovesystems.keycloak.firebasescrypt;

import com.smartmovesystems.keycloak.firebasescrypt.jpa.ScryptHashParametersEntity;

public class ScryptHashParametersRepresentation {

    public ScryptHashParametersRepresentation() {
    }

    public ScryptHashParametersRepresentation(ScryptHashParametersEntity entity) {
        id = entity.getId();
        rounds = entity.getRounds();
        memCost = entity.getMemCost();
        baser64Signer = entity.getBaser64Signer();
        saltSeparator = entity.getSaltSeparator();
        isDefault = entity.isDefault();
    }

    protected String id;

    private int rounds;

    private int memCost;

    private String baser64Signer;

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
}
