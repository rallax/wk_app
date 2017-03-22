package com.wk.app.rabbit;

import com.wk.app.message.DefaultMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author andrey.trotsenko
 */
@Component
public class MessageSenderIn extends MessageSender implements IMessageSender {
    @Value("${rabbitmq.queue.test_in}")
    private String queueTestNameIn;

    @Override
    public <T> void sendMessage(DefaultMessage<T> defaultMessage) {
        sendMessage(defaultMessage, queueTestNameIn);
    }
}
