package com.zmijewski.adam.addressbook.service;

import com.zmijewski.adam.addressbook.exception.EmailAlreadyExistException;
import com.zmijewski.adam.addressbook.mail.Mail;
import com.zmijewski.adam.addressbook.mail.MailSender;
import com.zmijewski.adam.addressbook.token.RegistrationToken;
import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RegistrationTokenService tokenService;
    private MailSender mailSender;
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setTokenService(RegistrationTokenService tokenService) {

        this.tokenService = tokenService;
    }
    @Autowired
    public void setMailSender(@Mail(type = Mail.MailType.REGISTRATION) MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Inside loadUserByName with mail: " + email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()){
            logger.warn("User with mail " + email + " not found");
            throw new UsernameNotFoundException("User not found");
        }
        if (!optionalUser.get().getConfirmed()){
            logger.warn("User with mail " + email + " not confirmed");
            throw new UsernameNotFoundException("User not confirmed");
        }
        User user = optionalUser.get();
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
        return userDetails;
    }
    public void registerUser(User user){
        logger.debug("Inside registerUser with user: " + user.getEmail());
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            logger.warn("User with mail " + user.getEmail() + " already exists in app");
            throw new EmailAlreadyExistException();
        }
        user.setConfirmed(false);
        String password = user.getPassword();
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        user = userRepository.save(user);
        RegistrationToken token = tokenService.createToken(user);
        tokenService.saveToken(token);
        sendMail(token, user);
    }
    public void confirmUser(RegistrationToken token){
        logger.debug("Inside confirmUser with token: " + token.getName());
        User user = token.getUser();
        user.setConfirmed(true);
        userRepository.save(user);
    }
    private void sendMail(RegistrationToken token, User user){
        logger.debug("Inside sendMail with user: " + user.getEmail());
        Thread thread = new Thread(() -> mailSender.sendMail(token, user));
        thread.start();
    }

}
