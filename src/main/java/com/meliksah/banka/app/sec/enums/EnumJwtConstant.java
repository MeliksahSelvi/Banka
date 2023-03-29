package com.meliksah.banka.app.sec.enums;

public enum EnumJwtConstant {

    BEARER("Bearer ");

    private final String constantName;

    EnumJwtConstant(String constantName) {
        this.constantName = constantName;
    }

    public String getConstantName() {
        return constantName;
    }

    @Override
    public String toString() {
        return constantName;
    }
}
