package com.zmijewski.adam.addressbook.mail;

import com.zmijewski.adam.addressbook.model.User;




public interface MailSender {
    void sendMail(String token, User user);
}
