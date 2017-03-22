package com.wk.app.rabbit;

import com.google.gson.Gson;
import com.wk.app.message.MessageBuilder;
import com.wk.app.message.DefaultMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author andrey.trotsenko
 */
@Component
public abstract class MessageSender {
    @Inject
    private RabbitTemplate rabbitTemplate;


    protected <T> void sendMessage(DefaultMessage<T> defaultMessage, String queue) {
        Message msg = new MessageBuilder().setBody(new Gson().toJson(defaultMessage)).build();
        rabbitTemplate.convertAndSend(queue, msg);
    }
}
