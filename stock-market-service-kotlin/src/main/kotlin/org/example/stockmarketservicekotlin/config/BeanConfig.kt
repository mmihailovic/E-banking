package org.example.stockmarketservicekotlin.config

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

@Configuration
class BeanConfig(
    @Value("\${update.balance.queue}")
    private val UPDATE_BALANCE_QUEUE: String,
    @Value("\${update.balance.exchange}")
    private val EXCHANGE: String,
    @Value("\${update.balance.routing.key}")
    private val UPDATE_BALANCE_ROUTING_KEY: String
) {
    @Bean
    fun alphaVantageRestTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.uriTemplateHandler = DefaultUriBuilderFactory("https://www.alphavantage.co/query")
        return restTemplate
    }

    @Bean
    fun bankServiceRestTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.uriTemplateHandler = DefaultUriBuilderFactory("http://localhost:8082/api")

        return restTemplate
    }

    @Bean
    fun converter(): SimpleMessageConverter {
        val converter = SimpleMessageConverter()
        converter.setAllowedListPatterns(listOf("*"))
        return converter
    }


    @Bean
    fun queueUpdateBalance(): Queue {
        return Queue(UPDATE_BALANCE_QUEUE, true)
    }

    @Bean
    fun exchange(): DirectExchange {
        return DirectExchange(EXCHANGE)
    }

    @Bean
    fun bind(): Binding {
        return BindingBuilder.bind(queueUpdateBalance())
            .to(exchange())
            .with(UPDATE_BALANCE_ROUTING_KEY)
    }
}