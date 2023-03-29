package com.meliksah.banka.app.acc.enums;

import com.meliksah.banka.app.gen.enums.BaseErrorMessage;

public enum AccErrorMessage implements BaseErrorMessage {
    INSUFFICIENT_BALANCE("Insufficient balance!");

    private final String message;

    AccErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
