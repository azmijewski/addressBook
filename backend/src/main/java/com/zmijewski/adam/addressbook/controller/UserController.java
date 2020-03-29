package com.zmijewski.adam.addressbook.controller;

import com.zmijewski.adam.addressbook.exception.EmailAlreadyExistException;
import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.service.UserService;
import com.zmijewski.adam.addressbook.token.JwtRequest;
import com.zmijewski.adam.addressbook.token.JwtResponse;
import com.zmijewski.adam.addressbook.token.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "${cross.url}")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    private JwtTokenUtil jwtTokenUtil;

    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
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
        userService.registerUser(user);
        return ResponseEntity
                .ok()
                .build();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid JwtRequest user, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        authenticate(user.getUsername(), user.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/register/{token}")
    public ResponseEntity<?> confirmAccount(@PathVariable ("token") String name){
        logger.info("Inside confirm account method");
        userService.confirmUser(name);
        return ResponseEntity
                .ok()
                .build();
    }
    private void authenticate(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e){
            throw new RuntimeException("USER_DISABLED");
        } catch (BadCredentialsException e){
            throw new RuntimeException("INVALID_CREDENTIALS");
        }

    }
}
