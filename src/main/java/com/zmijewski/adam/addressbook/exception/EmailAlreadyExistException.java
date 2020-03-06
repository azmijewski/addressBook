package com.zmijewski.adam.addressbook.exception;

public class EmailAlreadyExistException extends Exception{
    public EmailAlreadyExistException() {
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
