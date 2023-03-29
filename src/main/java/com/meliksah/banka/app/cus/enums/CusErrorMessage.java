package com.meliksah.banka.app.cus.enums;

import com.meliksah.banka.app.gen.enums.BaseErrorMessage;

public enum CusErrorMessage implements BaseErrorMessage {
    CUSTOMER_NOT_FOUND("Customer Not Found!");

    private final String message;

    CusErrorMessage(String message) {
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
