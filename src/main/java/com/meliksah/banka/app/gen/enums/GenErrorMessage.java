package com.meliksah.banka.app.gen.enums;

public enum GenErrorMessage implements BaseErrorMessage{
    ITEM_NOT_FOUND("Item Not Found!"),
    DATE_NOT_TO_BE_CONVERTED("Date Not To Be Converted!"),
    VALUE_NOT_BE_NEGATIVE("Value Not Be Negative!"),
    PARAMETER_CAN_NOT_BE_NULL("Parameter Can Not Be Null!");

    private final String message;

    GenErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
