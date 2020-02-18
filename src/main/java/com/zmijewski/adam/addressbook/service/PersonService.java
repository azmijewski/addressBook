package com.zmijewski.adam.addressbook.service;



import com.zmijewski.adam.addressbook.model.Person;
import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.repository.PersonRepository;
import com.zmijewski.adam.addressbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private UserRepository userRepository;
    @Autowired
    public PersonService(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public List<Person> getAll(){
        Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return personRepository.findAllByUser(user.get())
                .stream()
                .sorted((p1, p2) -> p1.getLastname().compareTo(p2.getLastname()))
                .collect(Collectors.toList());
    }
    public Optional<Person> getPersonById(Long id){
        Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return personRepository.findByIdAndUser(id, user.get());
    }
    public void save(Person person){
        Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        person.setUser(user.get());
        personRepository.save(person);
    }
    public List<Person> findAllByLastname(String lastname){
        Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return personRepository.findAllByLastnameAndUser(lastname, user.get());
    }
    public void delete(Long id){
        Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        personRepository.deleteByIdAndUser(id, user.get());
    }
    public void update(Person person){
        Optional<User> user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        person.setUser(user.get());
        personRepository.save(person);
    }
}
