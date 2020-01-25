package com.zmijewski.adam.addressbook.service;

import com.zmijewski.adam.addressbook.dto.AddressDto;
import com.zmijewski.adam.addressbook.model.Address;
import com.zmijewski.adam.addressbook.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private AddressRepository addressRepository;
    private PersonService personService;
    @Autowired
    public AddressService(AddressRepository addressRepository, PersonService personService) {
        this.addressRepository = addressRepository;
        this.personService = personService;
    }

    public void updateStreet(String street, Long personId){
        Address address = personService.getPersonById(personId).getAddress();
        addressRepository.updateStreet(street, address);
    }
    public void updateHouseNumber(String houseNumber, Long personId){
        Address address = personService.getPersonById(personId).getAddress();
        addressRepository.updateStreet(houseNumber, address);
    }
    public void updateCity(String city, Long personId){
        Address address = personService.getPersonById(personId).getAddress();
        addressRepository.updateStreet(city, address);
    }
}
