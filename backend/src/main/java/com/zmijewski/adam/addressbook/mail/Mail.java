package com.zmijewski.adam.addressbook.mail;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,
        ElementType.METHOD,
        ElementType.TYPE,
        ElementType.PARAMETER})
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Mail {
    public MailType type();
     public enum MailType{
         REGISTRATION
     }
}
