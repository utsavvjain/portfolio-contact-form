package com.portfolio.contact.beans;

import java.text.MessageFormat;

public class ContactForm {
    private String name;
    private String email;
    private String message;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Name : {0}\nEmail : {1}\nMessage : {2}", this.name, this.email, this.message);
    }

}
