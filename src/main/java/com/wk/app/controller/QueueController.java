package com.wk.app.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roman Luzko
 */
@RestController
public class QueueController {

    @Autowired
    AmqpTemplate template;

    @RequestMapping(value = "/queue",
            method = RequestMethod.GET, produces = "application/json")
    public void sendQueue() throws Exception {
        try {
            template.convertAndSend("QueueTest", "TestMessage1");
        } catch (Exception e) {
            throw new Exception("Нет доступа к очереди(RabbitMQ) - Host: " + " localhost" + " Очередь:" + " QueueTest");
        }
        System.out.println("send queue");
    }
}
