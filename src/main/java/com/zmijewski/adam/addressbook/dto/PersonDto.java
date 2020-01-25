package com.zmijewski.adam.addressbook.dto;

import com.zmijewski.adam.addressbook.model.Address;

import java.util.Optional;

public class PersonDto {
    private Optional<String> firstname;
    private Optional<String> lastname;
    private Optional<AddressDto> address;
    private Optional<ContactDto> contactDto;

    public PersonDto() {
    }

    public Optional<String> getFirstname() {
        return firstname;
    }

    public void setFirstname(Optional<String> firstname) {
        this.firstname = firstname;
    }

    public Optional<String> getLastname() {
        return lastname;
    }

    public void setLastname(Optional<String> lastname) {
        this.lastname = lastname;
    }

    public Optional<AddressDto> getAddress() {
        return address;
    }

    public void setAddress(Optional<AddressDto> address) {
        this.address = address;
    }

    public Optional<ContactDto> getContactDto() {
        return contactDto;
    }

    public void setContactDto(Optional<ContactDto> contactDto) {
        this.contactDto = contactDto;
    }
}
