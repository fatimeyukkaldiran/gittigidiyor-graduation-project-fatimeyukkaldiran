package dev.patika.patika0401.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String msg){
        super(msg);
    }
}
