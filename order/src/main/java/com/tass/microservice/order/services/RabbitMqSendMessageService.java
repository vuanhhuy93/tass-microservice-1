package com.tass.microservice.order.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqSendMessageService {
    @Autowired
    private AmqpTemplate amqpTemplate;


    public void sendMessageOrder(String message , String event){
        amqpTemplate.convertAndSend("ORDER_EXCHANGE" , "order." + event, message);
    }
}
