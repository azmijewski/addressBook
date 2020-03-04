package com.zmijewski.adam.addressbook.controller;

import com.zmijewski.adam.addressbook.model.Person;
import com.zmijewski.adam.addressbook.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Person>> getAll(){
        return ResponseEntity
                .ok(personService.getAll());
    }
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id, Model model){
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }
    @PostMapping("/contacts")
    public ResponseEntity<?> addContact(@RequestBody @Valid Person person, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        personService.save(person);
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
    public ResponseEntity<List<Person>> getByLastname(@RequestParam String lastname){
        return ResponseEntity
                .ok(personService.findAllByLastname(lastname));
    }
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        personService.delete(id);
        return ResponseEntity
                .ok()
                .build();
    }
    @PutMapping("/contacts/{id}")
    public ResponseEntity<?> updatePerson(@RequestBody @Valid Person person, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        personService.update(person);
        return ResponseEntity
                .ok()
                .build();
    }
}
