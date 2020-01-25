package com.zmijewski.adam.addressbook.service;

import com.zmijewski.adam.addressbook.dto.ContactDto;
import com.zmijewski.adam.addressbook.model.Contact;
import com.zmijewski.adam.addressbook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private ContactRepository contactRepository;
    private PersonService personService;
    @Autowired
    public ContactService(ContactRepository contactRepository, PersonService personService) {
        this.contactRepository = contactRepository;
        this.personService = personService;
    }

    public void updateMobileNumber(String mobileNumber, Long personId){
        Contact contact = personService.getPersonById(personId).getContact();
        contactRepository.updateMobileNumber(mobileNumber, contact);
    }
    public void updateEmail(String email, Long personId){
        Contact contact = personService.getPersonById(personId).getContact();
        contactRepository.updateEmail(email, contact);
    }

}
