package com.zmijewski.adam.addressbook.controller;

import com.zmijewski.adam.addressbook.exception.EmailAlreadyExistException;
import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        try {
            userService.registerUser(user);
        } catch (EmailAlreadyExistException e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/login")
    public Principal login(Principal user){

        return user;
    }
}
