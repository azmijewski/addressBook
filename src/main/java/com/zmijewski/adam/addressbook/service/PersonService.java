package com.zmijewski.adam.addressbook.service;

import com.zmijewski.adam.addressbook.model.Person;
import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.repository.PersonRepository;
import com.zmijewski.adam.addressbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(PersonService.class);
    @Autowired
    public PersonService(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public List<Person> getAll(String email){
        logger.info("Inside getAll");
        Optional<User> user = userRepository.findByEmail(email);
        logger.info("Founding all contacts for user: " + user.map(User::getEmail));

        return personRepository.findAllByUser(user.get())
                .stream()
                .sorted((p1, p2) -> p1.getLastname().compareTo(p2.getLastname()))
                .collect(Collectors.toList());
    }
    public Optional<Person> getPersonById(Long id, String email){
        logger.info("Inside getPersonById for id = " + id);
        Optional<User> user = userRepository.findByEmail(email);
        return personRepository.findByIdAndUser(id, user.get());
    }
    public void save(Person person, String email){
        logger.debug("Inside save");
        Optional<User> user = userRepository.findByEmail(email);
        person.setUser(user.get());
        personRepository.save(person);
    }
    public List<Person> findAllByLastname(String lastname, String email){
        logger.debug("Inside findAllByLastName for lastname = " + lastname);
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()){
            logger.warn("User with lastname = " + lastname + "not found" );
            throw new UsernameNotFoundException("user not found");
        }
        return personRepository.findAllByLastnameAndUser(lastname, user.get());
    }
    public void delete(Long id , String email){
        logger.debug("Deleting contact with id = " + id);
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()){
            logger.error("User with id = " + id + "not found" );
            throw new UsernameNotFoundException("user not found");
        }
        personRepository.deleteByIdAndUser(id, user.get());
    }
    public void update(Person person, String email){
        logger.info("Inside update with person: " + person.getId());
        Optional<User> user = userRepository.findByEmail(email);
        person.setUser(user.get());
        personRepository.save(person);
    }
}
