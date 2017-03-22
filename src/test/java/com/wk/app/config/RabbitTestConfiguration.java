package com.wk.app.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author andrey.trotsenko
 */
@PropertySource("classpath:config/application-test.properties")
@Configuration
@EnableRabbit
public class RabbitTestConfiguration {
    @Value("${rabbitmq.host}")
    private String rabbitMqHost;
    @Value("${rabbitmq.user}")
    private String rabbitMqUser;
    @Value("${rabbitmq.password}")
    private String rabbitMqPassword;
    @Value("${rabbitmq.queue.test_in}")
    private String queueTestNameIn;
    @Value("${rabbitmq.queue.test_out}")
    private String queueTestNameOut;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMqHost);
        connectionFactory.setUsername(rabbitMqUser);
        connectionFactory.setPassword(rabbitMqPassword);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue queueTestIn() {
        return new Queue(queueTestNameIn);
    }

    @Bean
    public Queue queueTestOut() {
        return new Queue(queueTestNameOut);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }
}
