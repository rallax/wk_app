package com.wk.app.queue;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitMqListener {

    @RabbitListener(containerFactory = "rabbitListenerContainerFactory", queues = "QueueTest")
    public void processReceive(String message) {
        System.out.println("Сообщение принято: " + message);
    }

}


