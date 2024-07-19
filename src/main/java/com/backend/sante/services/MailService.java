package com.backend.sante.services;


import com.backend.sante.entities.Jeune;

public interface MailService {
    void sendEmail(String to, String subject, String body);
    Jeune confirmEmail(String token);
    void sendConfirmationEmail(String to, String token);
}

