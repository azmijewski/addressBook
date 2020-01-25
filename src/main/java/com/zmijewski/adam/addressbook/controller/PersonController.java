package com.zmijewski.adam.addressbook.controller;
import com.zmijewski.adam.addressbook.dto.AddressDto;
import com.zmijewski.adam.addressbook.dto.ContactDto;
import com.zmijewski.adam.addressbook.dto.PersonDto;
import com.zmijewski.adam.addressbook.model.Person;
import com.zmijewski.adam.addressbook.service.AddressService;
import com.zmijewski.adam.addressbook.service.ContactService;
import com.zmijewski.adam.addressbook.service.PersonService;
import com.zmijewski.adam.addressbook.util.AddressToDtoConverter;
import com.zmijewski.adam.addressbook.util.ContactToDtoConverter;
import com.zmijewski.adam.addressbook.util.PersonToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {
    private PersonService personService;
    private AddressService addressService;
    private ContactService contactService;
    @Autowired
    public PersonController(PersonService personService, AddressService addressService, ContactService contactService) {
        this.personService = personService;
        this.addressService = addressService;
        this.contactService = contactService;
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
    @GetMapping("/addform")
    public String getForm(Model model){
        model.addAttribute("person", new Person());
        return "addform";
    }
    @PostMapping("/add")
    public String save(@ModelAttribute Person person, Model model){
        personService.savePerson(person);
        return "redirect:/people";
    }
    @GetMapping("/people/lastname")
    public String getByLastname(@RequestParam String lastname, Model model){
        List<Person> people = personService.findAllByLastname(lastname);
        model.addAttribute("people", people);
        return "people";
    }
    @PostMapping("/person/{id}/delete")
    public String delete(@PathVariable Long id){
        personService.deletePerson(id);
        return "redirect:/people";
    }
    @PostMapping("/person/{id}/update")
    public String updatePerson(@PathVariable Long id, Model model){
        String attribute = (String) model.getAttribute("attribute");
        String attributeValue = (String) model.getAttribute("attributeValue");
        System.out.println(attribute + " " + attributeValue);
        update(attribute, attributeValue, id);
        return "redirect:/person/" + id;
    }
    @GetMapping("/person/{id}/updateForm/{attribute}")
    public String updateForm(@PathVariable Long id, @PathVariable String attribute,  Model model){
        model.addAttribute("personId", id);
        model.addAttribute("attribute", attribute);
        model.addAttribute("attributeValue", "");
        return "updateform";
    }
    private void update(String attribute, String attributeValue ,Long personId){
        switch (attribute){
            case "firstname":
                personService.updateFirstname(attributeValue, personId);
                break;
            case "lastname":
                personService.updateLastname(attributeValue, personId);
                break;
            case "mobileNumber":
                contactService.updateMobileNumber(attributeValue, personId);
                break;
            case "email":
                contactService.updateEmail(attributeValue, personId);
                break;
            case "street":
                addressService.updateStreet(attributeValue, personId);
                break;
            case "houseNumber":
                addressService.updateHouseNumber(attributeValue, personId);
                break;
            case "city":
                addressService.updateCity(attributeValue, personId);
                break;
        }
    }

}
