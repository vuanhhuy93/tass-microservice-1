package com.tass.payment.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {
    @RabbitListener(queues = "PAYMENT_ORDER_QUEUE")
    public void listen(Message message) {
        System.out.println("Message read from myQueue : " + message.getBody());
    }
}
