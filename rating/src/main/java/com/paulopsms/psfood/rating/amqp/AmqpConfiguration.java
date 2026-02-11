package com.paulopsms.psfood.rating.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {

    @Bean
    public FanoutExchange createExchange() {
        return ExchangeBuilder.fanoutExchange("payment.ex").build();
    }

    @Bean
    public Queue createPaymentOrderDetailsQueue() {
        return QueueBuilder
                .nonDurable("payment.rating-details")
                .deadLetterExchange("payment.dlx")
                .build();
    }

    @Bean
    public Binding bindPaymentOrder() {
        return BindingBuilder.bind(this.createPaymentOrderDetailsQueue())
                .to(this.createExchange());
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeRabbitAdmin(RabbitAdmin rabbitAdmin) {
            return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }

    @Bean
    public FanoutExchange createDLX() {
        return ExchangeBuilder.fanoutExchange("payment.dlx").build();
    }

    @Bean
    public Queue createPaymentOrderDetailsDLQ() {
        return QueueBuilder.nonDurable("payment.rating-details-dlq").build();
    }

    @Bean
    public Binding bindPaymentOrderDLX() {
        return BindingBuilder.bind(this.createPaymentOrderDetailsDLQ())
                .to(this.createDLX());
    }
}
