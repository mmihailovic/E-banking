package com.example.transactionservice.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfiguration {
    @Value("${transactions.required.queue}")
    private String TRANSACTIONS_REQUIRED_QUEUE;

    @Value("${transactions.required.exchange}")
    private String EXCHANGE;

    @Value("${transactions.required.routing.key}")
    private String ROUTING_KEY;

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("*"));
        return converter;
    }
    @Bean
    public Queue queue() {
        return new Queue(TRANSACTIONS_REQUIRED_QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bind() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(ROUTING_KEY);
    }
}
