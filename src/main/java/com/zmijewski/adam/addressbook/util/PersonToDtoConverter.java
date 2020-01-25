package com.zmijewski.adam.addressbook.util;

import com.zmijewski.adam.addressbook.dto.PersonDto;
import com.zmijewski.adam.addressbook.model.Person;

import java.util.Optional;

public class PersonToDtoConverter implements AbstractConventerToDto<Person, PersonDto> {
    @Override
    public PersonDto convert(Person person) {
        PersonDto personDto= new PersonDto();
        personDto.setFirstname(Optional.ofNullable(person.getFirstname()));
        personDto.setLastname(Optional.ofNullable(person.getLastname()));

        return personDto;
    }
}
