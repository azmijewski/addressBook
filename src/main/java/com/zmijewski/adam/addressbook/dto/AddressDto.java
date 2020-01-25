package com.zmijewski.adam.addressbook.dto;

import java.util.Optional;

public class AddressDto {
    Optional<String> street;
    Optional<String> houseNumber;
    Optional<String> city;

    public AddressDto() {
    }

    public Optional<String> getStreet() {
        return street;
    }

    public void setStreet(Optional<String> street) {
        this.street = street;
    }

    public Optional<String> getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Optional<String> houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Optional<String> getCity() {
        return city;
    }

    public void setCity(Optional<String> city) {
        this.city = city;
    }
}
