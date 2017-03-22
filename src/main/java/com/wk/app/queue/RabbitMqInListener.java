package com.wk.app.queue;

import com.google.gson.Gson;
import com.wk.app.message.DefaultSmsMessage;
import com.wk.app.service.SmsBillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class RabbitMqInListener {
    Logger logger = LoggerFactory.getLogger(RabbitMqInListener.class);

    @Inject
    SmsBillingService smsBillingService;

    @RabbitListener(containerFactory = "rabbitListenerContainerFactory", queues = "${rabbitmq.queue.test_in}")
    public void processReceive(Message message) {
        logger.info("Message received: {}", message.toString());

        try {
            //noinspection unchecked
            DefaultSmsMessage defaultMessage = new Gson().fromJson(new String(message.getBody()), DefaultSmsMessage.class);
            smsBillingService.calculateAndSave(defaultMessage.getBody());
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
    }
}


