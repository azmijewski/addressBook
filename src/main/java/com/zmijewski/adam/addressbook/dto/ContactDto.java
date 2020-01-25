package com.zmijewski.adam.addressbook.dto;

import java.util.Optional;

public class ContactDto {
    Optional<String> mobileNumber;
    Optional<String> email;

    public ContactDto() {
    }

    public Optional<String> getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Optional<String> mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }
}
