package com.fatimeyuk.customerservice.exceptions;

public class DateFormatterWrongException extends RuntimeException{
    public DateFormatterWrongException(String message){
        super(message);
    }
}
