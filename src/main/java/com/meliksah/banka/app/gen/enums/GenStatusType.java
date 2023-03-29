package com.meliksah.banka.app.gen.enums;

public enum GenStatusType {

    ACTIVE("Active"),
    PASSIVE("Passive");

    private final String statusType;

    GenStatusType(String statusType) {
        this.statusType = statusType;
    }

    @Override
    public String toString() {
        return statusType;
    }
}
