package com.zmijewski.adam.addressbook.mail;

import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.token.Token;
import org.springframework.stereotype.Component;


public interface MailSender {
    public void sendMail(Token token, User user);
}
