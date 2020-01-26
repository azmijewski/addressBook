package com.zmijewski.adam.addressbook.controller;

import com.zmijewski.adam.addressbook.model.Person;
import com.zmijewski.adam.addressbook.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/people")
    public String getAll(Model model){
        List<Person> people = personService.getAll();
        model.addAttribute("people", people);
        return "people";
    }
    @GetMapping("/person/{id}")
    public String getOne(@PathVariable Long id, Model model){
        Person person = personService.getPersonById(id);
        model.addAttribute("person", person);
        return "person";
    }
    @GetMapping("/add-contact")
    public String getForm(Model model){
        model.addAttribute("person", new Person());
        return "addform";
    }
    @PostMapping("/save-contact")
    public String save(@ModelAttribute Person person, Model model){
        personService.save(person);
        return "redirect:/people";
    }
    @GetMapping("/search")
    public String getByLastname(@RequestParam String lastname, Model model){
        List<Person> people = personService.findAllByLastname(lastname);
        model.addAttribute("people", people);
        return "people";
    }
    @PostMapping("/person-delete/{id}")
    public String delete(@PathVariable Long id){
        personService.delete(id);
        return "redirect:/people";
    }
    @PostMapping("/person-update")
    public String updatePerson(@ModelAttribute Person person, Model model){
        personService.update(person);
        return "redirect:/people";
    }
    @GetMapping("/person-update/{id}")
    public String updateForm(@PathVariable Long id,  Model model){
       Person person = personService.getPersonById(id);
       model.addAttribute("person", person);
        return "updateform";
    }


}
