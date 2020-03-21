package com.zmijewski.adam.addressbook.controller;

import com.zmijewski.adam.addressbook.service.RegistrationTokenService;
import com.zmijewski.adam.addressbook.service.UserService;
import com.zmijewski.adam.addressbook.token.RegistrationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "${cross.url}")
public class RegistrationTokenController {

    private static Logger logger = LoggerFactory.getLogger(RegistrationTokenController.class);

    private UserService userService;
    private RegistrationTokenService registrationTokenService;

    @Autowired
    public RegistrationTokenController(UserService userService, RegistrationTokenService registrationTokenService) {
        this.userService = userService;
        this.registrationTokenService = registrationTokenService;
    }
    @GetMapping("/register/{token}")
    public ResponseEntity<RegistrationToken> confirmAccount(@PathVariable("token") String name){
        logger.info("Inside confirm account method");
        Optional<RegistrationToken> token = registrationTokenService.findByName(name);
        token.ifPresent(userService::confirmUser);
        return token
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }
}
