package com.fatimeyuk.customerservice.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String msg){
        super(msg);
    }
}
