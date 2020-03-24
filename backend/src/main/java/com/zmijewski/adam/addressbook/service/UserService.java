package com.zmijewski.adam.addressbook.service;

import com.zmijewski.adam.addressbook.exception.EmailAlreadyExistException;
import com.zmijewski.adam.addressbook.exception.RegistrationTokenExpiredException;
import com.zmijewski.adam.addressbook.mail.Mail;
import com.zmijewski.adam.addressbook.mail.MailSender;
import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.repository.UserRepository;
import com.zmijewski.adam.addressbook.token.RegistrationTokenUtil;
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
    private MailSender mailSender;
    private RegistrationTokenUtil tokenUtil;
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
    public void setMailSender(@Mail(type = Mail.MailType.REGISTRATION) MailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Autowired
    public void setTokenUtil(RegistrationTokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Inside loadUserByName with mail: " + email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()){
            logger.error("User with mail " + email + " not found");
            throw new UsernameNotFoundException("User not found");
        }
        if (!optionalUser.get().getConfirmed()){
            logger.error("User with mail " + email + " not confirmed");
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
    public void registerUser(User user) throws EmailAlreadyExistException {
        logger.info("Inside registerUser with user: " + user.getEmail());
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            logger.error("User with mail " + user.getEmail() + " already exists in app");
            throw new EmailAlreadyExistException();
        }
        user.setConfirmed(false);
        String password = user.getPassword();
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        user = userRepository.save(user);
        String token = tokenUtil.generateToken(user.getEmail());
        sendMail(token, user);
    }
    public void confirmUser(String token){
        String username = tokenUtil.getUsernameFromToken(token);
        logger.info("Inside confirmUser with token: " + username);
        if (tokenUtil.validateToken(token)){
            Optional<User> user = userRepository.findByEmail(username);
            user.ifPresent(user1 -> confirmUser(user1));
        } else{
            throw new RegistrationTokenExpiredException("Token is expired");
        }
    }
    private void sendMail(String token, User user){
        Thread thread = new Thread(() -> mailSender.sendMail(token, user));
        thread.start();
    }
    private void confirmUser(User user){
        user.setConfirmed(true);
        userRepository.save(user);
    }

}
