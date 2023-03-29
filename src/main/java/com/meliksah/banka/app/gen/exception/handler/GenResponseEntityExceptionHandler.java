package com.meliksah.banka.app.gen.exception.handler;

import com.meliksah.banka.app.gen.dto.RestResponse;
import com.meliksah.banka.app.gen.exception.exceptions.GenBusinessException;
import com.meliksah.banka.app.gen.exception.exceptions.ItemNotFoundException;
import com.meliksah.banka.app.gen.exception.response.GenExceptionResponse;
import com.meliksah.banka.app.kafka.dto.LogMessage;
import com.meliksah.banka.app.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
public class GenResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final LogService logService;

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllItemNotFoundException(ItemNotFoundException ex, WebRequest webRequest) {

        Date errorDate = new Date();
        String message = ex.getBaseErrorMessage().getMessage();
        String detail = webRequest.getDescription(false);

        GenExceptionResponse genExceptionResponse = new GenExceptionResponse(errorDate, message, detail);

        RestResponse<GenExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
        restResponse.setMessages(message);

        //hataları kafka ile message log yapıyoruz.
        logError(genExceptionResponse);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllGenBusinessException(GenBusinessException ex, WebRequest webRequest) {

        Date errorDate = new Date();
        String message = ex.getBaseErrorMessage().getMessage();
        String detail = webRequest.getDescription(false);

        GenExceptionResponse genExceptionResponse = new GenExceptionResponse(errorDate, message, detail);

        RestResponse<GenExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
        restResponse.setMessages(message);

        logError(genExceptionResponse);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    private void logError(GenExceptionResponse genExceptionResponse) {
        String message = genExceptionResponse.getMessage();
        String detail = genExceptionResponse.getDetail();

        LogMessage logMessage = LogMessage.builder()
                .message(message)
                .dateTime(genExceptionResponse.getErrorDate())
                .description(detail)
                .build();

        logService.log(logMessage);
    }
}
