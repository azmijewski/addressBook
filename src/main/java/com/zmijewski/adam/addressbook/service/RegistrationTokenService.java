package com.zmijewski.adam.addressbook.service;

import com.zmijewski.adam.addressbook.model.RegistrationToken;
import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.repository.RegistrationTokenRepository;
import com.zmijewski.adam.addressbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationTokenService {
    private RegistrationTokenRepository tokenRepository;
    private UserRepository userRepository;
    @Autowired
    public RegistrationTokenService(RegistrationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    public RegistrationToken createToken(User user){
        RegistrationToken token = new RegistrationToken();
        token.setUser(user);
        return token;
    }
    public void saveToken(RegistrationToken token){
        tokenRepository.save(token);
    }
    @Scheduled(fixedRate = 1000 * 60 )
    public void deleteExpiredTokens(){
        List<RegistrationToken> tokens = tokenRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (RegistrationToken token : tokens){
            if (token.getExpirationDate().isAfter(now)){
                User user = token.getUser();
                userRepository.delete(user);
                tokenRepository.delete(token);
            }
        }
    }
}
