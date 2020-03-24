package com.zmijewski.adam.addressbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistrationTokenExpiredException extends RuntimeException {
    public RegistrationTokenExpiredException() {
    }

    public RegistrationTokenExpiredException(String message) {
        super(message);
    }
}
