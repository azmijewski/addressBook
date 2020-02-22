package com.zmijewski.adam.addressbook.controller;

import com.zmijewski.adam.addressbook.service.RegistrationTokenService;
import com.zmijewski.adam.addressbook.service.UserService;
import com.zmijewski.adam.addressbook.token.RegistrationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class TokenController {
    private UserService userService;
    private RegistrationTokenService registrationTokenService;
    @Autowired
    public TokenController(UserService userService, RegistrationTokenService registrationTokenService) {
        this.userService = userService;
        this.registrationTokenService = registrationTokenService;
    }
    @GetMapping("/register/{token}")
    public String confirmAccount(@PathVariable("token") String name){
        Optional<RegistrationToken> token = registrationTokenService.findByName(name);
        if (token.isPresent()){
            userService.confirmUser(token.get());
            return "registerConfirmed";
        }
        return "registerNonConfirmed";
    }
}
