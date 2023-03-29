package com.meliksah.banka.app.gen.exception.exceptions;

import com.meliksah.banka.app.gen.enums.BaseErrorMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenBusinessException extends RuntimeException{

    private final BaseErrorMessage baseErrorMessage;
}
