package com.fatimeyuk.customerservice.exceptions;

import com.fatimeyuk.customerservice.entity.ErrorExceptionLogger;
import com.fatimeyuk.customerservice.service.ErrorExceptionLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Date;
import java.time.LocalDate;

@RestControllerAdvice
public class GlobalExceptionHandler {
    ErrorExceptionLoggerService errorExceptionLoggerService;

    @Autowired
    public GlobalExceptionHandler(ErrorExceptionLoggerService errorExceptionLoggerService) {
        this.errorExceptionLoggerService = errorExceptionLoggerService;
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorExceptionLogger> handleException(BadRequestException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomerWithPhoneNumberIsAlreadyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorExceptionLogger> handleException(CustomerWithPhoneNumberIsAlreadyException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    private ErrorExceptionLogger getErrorResponse(HttpStatus httpStatus,String message) {
        ErrorExceptionLogger response = new ErrorExceptionLogger();
        response.setStatus(httpStatus.value());
        response.setErrorMessage((message));
        response.setExceptionDate(Date.valueOf(LocalDate.now()));

        return errorExceptionLoggerService.save(response).get();
    }
}
