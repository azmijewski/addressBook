package com.zmijewski.adam.addressbook.service;

import com.zmijewski.adam.addressbook.token.RegistrationToken;
import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.repository.RegistrationTokenRepository;
import com.zmijewski.adam.addressbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationTokenService {
    private RegistrationTokenRepository tokenRepository;
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(RegistrationTokenService.class);
    @Autowired
    public RegistrationTokenService(RegistrationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    public RegistrationToken createToken(User user){
        logger.debug("Inside createToken for user: " + user.getEmail());
        RegistrationToken token = new RegistrationToken();
        token.setUser(user);
        return token;
    }
    public void saveToken(RegistrationToken token){
        logger.debug("Inside saveToken with token: " + token.getName());
        tokenRepository.save(token);
    }
    public Optional<RegistrationToken> findByName(String name){
        logger.debug("Inside findByName with token: " + name);
        return tokenRepository.findByName(name);
    }
    @Scheduled(fixedRate = 1000 * 60 )
    public void deleteExpiredTokens(){
        logger.debug("Inside scheduled deleteExpiredTokens");
        List<RegistrationToken> tokens = tokenRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (RegistrationToken token : tokens){
            if (token.getExpirationDate().isAfter(now)){
                logger.debug("Token with name " + token.getName() + " is going to be removed");
                tokenRepository.delete(token);
            }
        }
    }
}
