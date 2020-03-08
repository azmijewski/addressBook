package com.zmijewski.adam.addressbook.controller;

import com.zmijewski.adam.addressbook.exception.EmailAlreadyExistException;
import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user, BindingResult result){
        logger.info("Registering new user");
        if (result.hasErrors()){
            logger.error("Invalid user data");
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        try {
            userService.registerUser(user);
        } catch (EmailAlreadyExistException e) {
            logger.error("User with email " + user.getEmail() + " already exists in system");
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
        logger.info("Inside login method");
        return user;
    }
}
