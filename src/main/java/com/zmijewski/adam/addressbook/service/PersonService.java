package com.zmijewski.adam.addressbook.service;



import com.zmijewski.adam.addressbook.model.Person;
import com.zmijewski.adam.addressbook.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public List<Person> getAll(){
        return personRepository.findAll()
                .stream()
                .sorted((p1, p2) -> p1.getLastname().compareTo(p2.getLastname()))
                .collect(Collectors.toList());
    }
    public Person getPersonById(Long id){
        return personRepository.getOne(id);
    }
    public void save(Person person){
        personRepository.save(person);
    }
    public List<Person> findAllByLastname(String lastname){
        return personRepository.findAllByLastname(lastname);
    }
    public void delete(Long id){
        personRepository.deleteById(id);
    }
    public void update(Person person){
        personRepository.save(person);
    }
}
