package com.zmijewski.adam.addressbook.controller;

import com.zmijewski.adam.addressbook.model.Person;
import com.zmijewski.adam.addressbook.service.PersonService;
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
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Person>> getAll(Principal principal){
        return ResponseEntity
                .ok(personService.getAll(principal.getName()));
    }
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id, Principal principal){
        return personService.getPersonById(id, principal.getName())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }
    @PostMapping("/contacts")
    public ResponseEntity<?> addContact(@RequestBody @Valid Person person, Principal principal, BindingResult result){
        if (result.hasErrors()){
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
        return ResponseEntity
                .ok(personService.findAllByLastname(lastname, principal.getName()));
    }
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal){
        personService.delete(id, principal.getName());
        return ResponseEntity
                .ok()
                .build();
    }
    @PutMapping("/contacts/{id}")
    public ResponseEntity<?> updatePerson(@RequestBody @Valid Person person, Principal principal, BindingResult result){
        if (result.hasErrors()){
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
