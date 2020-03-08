package com.zmijewski.adam.addressbook.mail;

import com.zmijewski.adam.addressbook.model.User;
import com.zmijewski.adam.addressbook.token.RegistrationToken;
import com.zmijewski.adam.addressbook.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Mail(type = Mail.MailType.REGISTRATION)
@Component
public class RegistrationTokenMailSender implements MailSender {
    @Value("${app.registration.url}")
    private String appUrl;

    private static Logger logger = LoggerFactory.getLogger(RegistrationTokenMailSender.class);

    private JavaMailSender mailSender;
    @Autowired
    public RegistrationTokenMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(Token token, User user) {
        logger.info("Sending mail to " + user.getEmail());
        RegistrationToken rgtoken = (RegistrationToken) token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setText(prepareMessageBody(rgtoken));
        mailMessage.setSubject("AddressBook - Weryfikacja konta");
        mailSender.send(mailMessage);
    }
    private String prepareMessageBody(RegistrationToken token){
        
        return "Link do potwierdzenia konta:\n" + appUrl + token.getName() + "\n Jezeli nie rejestrowales sie na stronie, po prostu zignoruj tę wiadomość";
    }
}
