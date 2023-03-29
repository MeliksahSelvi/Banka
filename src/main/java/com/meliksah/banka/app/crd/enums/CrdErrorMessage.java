package com.meliksah.banka.app.crd.enums;

import com.meliksah.banka.app.gen.enums.BaseErrorMessage;

public enum CrdErrorMessage implements BaseErrorMessage {
    INVALID_CARD("Invalid Credit Card!"),
    INSUFFICIENT_CREDIT_CARD_LIMIT("Insufficient Credit Card!"),
    CREDIT_CARD_EXPIRED("Credit Card Expired!");

    private final String message;

    CrdErrorMessage(String message) {
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
