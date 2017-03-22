package com.wk.app.queue;

import com.google.gson.Gson;
import com.wk.app.message.DefaultSmsBillingRecordMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqOutTestListener {
    Logger logger = LoggerFactory.getLogger(RabbitMqOutTestListener.class);

    @RabbitListener(containerFactory = "rabbitListenerContainerFactory", queues = "${rabbitmq.queue.test_out}")
    public void processSmsBillingRecordReceive(Message message) {
        logger.info("Message received: ", message.toString());
        DefaultSmsBillingRecordMessage res = new Gson().fromJson(new String(message.getBody()), DefaultSmsBillingRecordMessage.class);
    }

}


