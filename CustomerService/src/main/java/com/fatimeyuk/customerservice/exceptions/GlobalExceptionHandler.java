package com.fatimeyuk.customerservice.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fatimeyuk.customerservice.entity.ErrorExceptionLogger;
import com.fatimeyuk.customerservice.service.ErrorExceptionLoggerService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    ErrorExceptionLoggerService errorExceptionLoggerService;

    @Autowired
    public GlobalExceptionHandler(ErrorExceptionLoggerService errorExceptionLoggerService) {
        this.errorExceptionLoggerService = errorExceptionLoggerService;
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorExceptionLogger handleException(BadRequestException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return response;
    }

    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorExceptionLogger handleException(CustomerNotFoundException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.NOT_FOUND, exc.getMessage());
        return response;
    }


    @ExceptionHandler({CustomerWithPhoneNumberIsAlreadyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorExceptionLogger handleException(CustomerWithPhoneNumberIsAlreadyException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return response;
    }

    @ExceptionHandler({CustomerWithNationalIdIsAlreadyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorExceptionLogger handleException(CustomerWithNationalIdIsAlreadyException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return response;
    }

    @ExceptionHandler({JsonParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorExceptionLogger handleException(JsonParseException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return response;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public String handleException(HttpMessageNotReadableException exc){
        return "You input miss knowledge";
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ErrorExceptionLogger handleException(HttpRequestMethodNotSupportedException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, exc.getMessage());
        return response;
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorExceptionLogger handleException(DataIntegrityViolationException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.NOT_ACCEPTABLE, exc.getMessage());
        return response;
    }

    @ExceptionHandler({MissingPathVariableException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorExceptionLogger handleException(MissingPathVariableException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.NOT_ACCEPTABLE, exc.getMessage());
        return response;
    }

    @ExceptionHandler({DateFormatterWrongException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorExceptionLogger handleException(DateFormatterWrongException exc){
        ErrorExceptionLogger response = getErrorResponse(HttpStatus.NOT_ACCEPTABLE, exc.getMessage());
        return response;
    }




    private ErrorExceptionLogger getErrorResponse(HttpStatus httpStatus,String message) {
        ErrorExceptionLogger response = new ErrorExceptionLogger();
        response.setStatusCode(httpStatus.value());
        response.setErrorMessage((message));
        response.setExceptionDate(LocalDate.now());

        return errorExceptionLoggerService.save(response).get();
    }
}
