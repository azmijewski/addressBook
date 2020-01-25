package com.zmijewski.adam.addressbook.util;

import com.zmijewski.adam.addressbook.dto.ContactDto;
import com.zmijewski.adam.addressbook.model.Contact;

import java.util.Optional;

public class ContactToDtoConverter implements AbstractConventerToDto<Contact, ContactDto>{
    @Override
    public ContactDto convert(Contact contact) {
        ContactDto contactDto = new ContactDto();
        contactDto.setMobileNumber(Optional.ofNullable(contact.getMobileNumber()));
        contactDto.setEmail(Optional.of(contact.getEmail()));
        return contactDto;
    }
}
