package com.first.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String recipient, String subject, String content) throws MessagingException;

}
