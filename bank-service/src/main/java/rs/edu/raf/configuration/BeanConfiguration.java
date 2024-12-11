package rs.edu.raf.configuration;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class BeanConfiguration {
    @Value("${transactions.success.queue}")
    private String TRANSACTIONS_SUCCESS_QUEUE;

    @Value("${transactions.failed.queue}")
    private String TRANSACTIONS_FAILED_QUEUE;

    @Value("${transactions.exchange}")
    private String EXCHANGE;

    @Value("${transactions.success.routing.key}")
    private String SUCCESS_ROUTING_KEY;

    @Value("${transactions.failed.routing.key}")
    private String FAILED_ROUTING_KEY;

    @Bean
    public RestTemplate userServiceRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/api"));

        return restTemplate;
    }

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put("bootstrap.servers", "localhost:9092");
        producerProps.put("key.serializer", StringSerializer.class);
        producerProps.put("value.serializer", StringSerializer.class);
        producerProps.put("acks", "all");
        producerProps.put("retries", Integer.MAX_VALUE);
        producerProps.put("linger.ms", 1);
        producerProps.put("enable.idempotence", "true");

        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("*"));
        return converter;
    }


    @Bean
    public Queue queueSuccessTransaction() {
        return new Queue(TRANSACTIONS_SUCCESS_QUEUE, true);
    }

    @Bean
    public Queue queueFailedTransaction() {
        return new Queue(TRANSACTIONS_FAILED_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bindSuccess() {
        return BindingBuilder.bind(queueSuccessTransaction())
                .to(exchange())
                .with(SUCCESS_ROUTING_KEY);
    }

    @Bean
    public Binding bindFailed() {
        return BindingBuilder.bind(queueFailedTransaction())
                .to(exchange())
                .with(FAILED_ROUTING_KEY);
    }

}
