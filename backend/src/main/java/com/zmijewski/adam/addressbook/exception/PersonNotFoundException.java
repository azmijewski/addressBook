package com.zmijewski.adam.addressbook.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
        super();
    }

    public PersonNotFoundException(String message) {
        super(message);
    }
}
