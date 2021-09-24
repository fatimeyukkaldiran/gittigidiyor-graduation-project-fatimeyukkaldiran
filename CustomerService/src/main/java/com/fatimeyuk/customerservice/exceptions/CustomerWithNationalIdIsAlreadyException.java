package com.fatimeyuk.customerservice.exceptions;

public class CustomerWithNationalIdIsAlreadyException extends RuntimeException{
    public CustomerWithNationalIdIsAlreadyException(String message){
        super(message);
    };
}
