package com.tass.payment.configs;

import java.util.UUID;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmpqConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(@Autowired ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    Queue queue() {

        Queue queue = new Queue("pubsub-order-" + UUID.randomUUID().toString(), false, true, true);

        queue.setShouldDeclare(true);


        return queue;
    }
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange("ORDER_EXCHANGE");
    }
    @Bean
    Binding binding(Queue queue , TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("order.*");
    }
}
