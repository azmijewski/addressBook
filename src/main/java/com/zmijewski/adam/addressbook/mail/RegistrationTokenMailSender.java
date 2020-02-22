package com.zmijewski.adam.addressbook.mail;

import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.token.RegistrationToken;
import com.zmijewski.adam.addressbook.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Mail(type = Mail.MailType.REGISTRATION)
@Component
public class RegistrationTokenMailSender implements MailSender {
    @Value("${app.registration.url}")
    private String appUrl;

    private JavaMailSender mailSender;
    @Autowired
    public RegistrationTokenMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(Token token, User user) {
        RegistrationToken rgtoken = (RegistrationToken) token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setText(textMessage(rgtoken));
        mailMessage.setSubject("Weryfikacja");
        mailSender.send(mailMessage);
    }
    private String textMessage(RegistrationToken token){
        return appUrl + token.getName();
    }
}
