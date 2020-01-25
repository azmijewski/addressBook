package com.zmijewski.adam.addressbook.service;


import com.zmijewski.adam.addressbook.dto.PersonDto;
import com.zmijewski.adam.addressbook.model.Person;
import com.zmijewski.adam.addressbook.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PersonService {
    private PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public List<Person> getAll(){
        return personRepository.findAll();
    }
    public Person getPersonById(Long id){
        return personRepository.getOne(id);
    }
    public void savePerson(Person person){
        personRepository.save(person);
    }
    public List<Person> findAllByLastname(String lastname){
        return personRepository.findAllByLastname(lastname);
    }
    public void deletePerson(Long id){
        personRepository.deleteById(id);
    }
    public void updateFirstname(String firstname, Long personId){
       Person person = personRepository.getOne(personId);
       personRepository.updateFirstname(firstname, person);
    }
    public void updateLastname(String lastname, Long id){
       Person person = personRepository.getOne(id);
       personRepository.updateLastname(lastname, person);
    }
}
