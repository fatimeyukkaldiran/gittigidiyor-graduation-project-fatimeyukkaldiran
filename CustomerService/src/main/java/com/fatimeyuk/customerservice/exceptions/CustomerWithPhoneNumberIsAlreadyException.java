package com.fatimeyuk.customerservice.exceptions;

public class CustomerWithPhoneNumberIsAlreadyException extends RuntimeException {
    public CustomerWithPhoneNumberIsAlreadyException(String message){
        super(message);
    }
}
