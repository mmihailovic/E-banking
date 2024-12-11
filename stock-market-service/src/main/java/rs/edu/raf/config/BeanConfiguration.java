package rs.edu.raf.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

@Configuration
public class BeanConfiguration {
    @Value("${update.balance.queue}")
    private String UPDATE_BALANCE_QUEUE;

    @Value("${update.balance.exchange}")
    private String EXCHANGE;

    @Value("${update.balance.routing.key}")
    private String UPDATE_BALANCE_ROUTING_KEY;

    @Bean
    public RestTemplate alphaVantageRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("https://www.alphavantage.co/query"));
        return restTemplate;
    }

    @Bean
    public RestTemplate bankServiceRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8082/api"));

        return restTemplate;
    }

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("*"));
        return converter;
    }


    @Bean
    public Queue queueUpdateBalance() {
        return new Queue(UPDATE_BALANCE_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bind() {
        return BindingBuilder.bind(queueUpdateBalance())
                .to(exchange())
                .with(UPDATE_BALANCE_ROUTING_KEY);
    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        return new SimpleRoutingConnectionFactory();
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.setConnectionFactory(connectionFactory());
//        return new RabbitTemplate();
//    }

}
