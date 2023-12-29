package org.mql.accountservice.service.impl;

/*import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;*/
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    /*private final AmqpTemplate amqpTemplate;

    @Autowired
    public RabbitMQService(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessage(String message) {
        amqpTemplate.convertAndSend("account-balance-queue", message);
        System.out.println("Message sent to RabbitMQ: " + message);
    }*/
}