package com.first.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.decorator.EmailDetails;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@RequiredArgsConstructor
@Component
public class EmailConsumer {

    private final EmailSenderService emailSenderService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.queue.name}"),
            exchange = @Exchange(value = "${spring.rabbitmq.exchange.name}"),
            key = "${spring.rabbitmq.routing-key}"
    ))
    public void receiveEmailNotification(byte[] data) throws IOException {
        EmailDetails emailDetails = new ObjectMapper().readValue(data, EmailDetails.class);
        try {
            emailSenderService.sendEmail(emailDetails.getRecipient(), emailDetails.getSubject(), emailDetails.getContent());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
