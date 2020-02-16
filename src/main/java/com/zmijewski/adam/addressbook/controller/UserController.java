package com.zmijewski.adam.addressbook.controller;

import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "registerForm";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute @Valid User user, BindingResult result, Model model){
        if (result.hasErrors()){
            return "registerForm";
        }
        userService.registerUser(user);
        return "registerSuccesfull";
    }
}
