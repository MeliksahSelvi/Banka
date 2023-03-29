package com.meliksah.banka.app.gen.exception.exceptions;

import com.meliksah.banka.app.gen.enums.BaseErrorMessage;
import com.meliksah.banka.app.gen.enums.GenErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends GenBusinessException {

    public ItemNotFoundException(BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }
}
