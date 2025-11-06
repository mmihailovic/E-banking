package org.example.bankservicekotlin.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.SimpleMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory
import java.security.SecureRandom

@Configuration
class BeanConfiguration {
    @Value("\${transactions.success.queue}")
    lateinit var TRANSACTIONS_SUCCESS_QUEUE: String

    @Value("\${transactions.failed.queue}")
    lateinit var TRANSACTIONS_FAILED_QUEUE: String

    @Value("\${transactions.exchange}")
    lateinit var EXCHANGE: String

    @Value("\${transactions.success.routing.key}")
    lateinit var SUCCESS_ROUTING_KEY: String

    @Value("\${transactions.failed.routing.key}")
    lateinit var FAILED_ROUTING_KEY: String

    @Bean
    fun userServiceRestTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.uriTemplateHandler = DefaultUriBuilderFactory("http://localhost:8080/api")

        return restTemplate
    }

    @Bean
    fun secureRandom(): SecureRandom {
        return SecureRandom()
    }

    @Bean
    fun converter(): SimpleMessageConverter {
        val converter = SimpleMessageConverter()
        converter.setAllowedListPatterns(listOf("*"))
        return converter
    }


    @Bean
    fun queueSuccessTransaction(): Queue {
        return Queue(TRANSACTIONS_SUCCESS_QUEUE, true)
    }

    @Bean
    fun queueFailedTransaction(): Queue {
        return Queue(TRANSACTIONS_FAILED_QUEUE, true)
    }

    @Bean
    fun exchange(): DirectExchange {
        return DirectExchange(EXCHANGE)
    }

    @Bean
    fun bindSuccess(): Binding {
        return BindingBuilder.bind(queueSuccessTransaction())
            .to(exchange())
            .with(SUCCESS_ROUTING_KEY)
    }

    @Bean
    fun bindFailed(): Binding {
        return BindingBuilder.bind(queueFailedTransaction())
            .to(exchange())
            .with(FAILED_ROUTING_KEY)
    }
}