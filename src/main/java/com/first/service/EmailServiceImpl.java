package com.first.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String recipient, String subject, String content) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom("kira3092650@gmail.com");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setContent(content, "text/html");

        javaMailSender.send(message);
        System.out.println("Mail send successfully to "+ recipient);
    }
}
