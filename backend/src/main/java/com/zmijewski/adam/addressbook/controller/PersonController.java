package com.zmijewski.adam.addressbook.controller;

import com.zmijewski.adam.addressbook.model.Person;
import com.zmijewski.adam.addressbook.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "${cross.url}")
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Person>> getAll(Principal principal){
        logger.info("Getting all contacts for user: " + principal.getName());
        return ResponseEntity
                .ok(personService.getAll(principal.getName()));
    }
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id, Principal principal){
        logger.info("Getting contact with id = " + id +" for user: " + principal.getName());
        return personService.getPersonById(id, principal.getName())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }
    @PostMapping("/contacts")
    public ResponseEntity<?> addContact(@RequestBody @Valid Person person, Principal principal, BindingResult result){
        logger.info("Adding new contact for user: " + principal.getName());
        if (result.hasErrors()){
            logger.error("Invalid contact was trying to add");
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        personService.save(person, principal.getName());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(person.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .build();
    }
    @GetMapping("/contacts/search")
    public ResponseEntity<List<Person>> getByLastname(@RequestParam String lastname, Principal principal){
        logger.info("Searching contact with lastname = " + lastname + " for user: " + principal.getName());
        return ResponseEntity
                .ok(personService.findAllByLastname(lastname, principal.getName()));
    }
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal){
        logger.info("Deleting contact with id = " + id + " for user: " + principal.getName());
        personService.delete(id, principal.getName());
        return ResponseEntity
                .ok()
                .build();
    }
    @PutMapping("/contacts/{id}")
    public ResponseEntity<?> updatePerson(@RequestBody @Valid Person person, Principal principal, BindingResult result){
        logger.info("Updating contact with id = " + person.getId() + " for user: " + principal.getName());
        if (result.hasErrors()){
            logger.error("Invalid contact was trying to update");
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        personService.update(person, principal.getName());
        return ResponseEntity
                .ok()
                .build();
    }
}
