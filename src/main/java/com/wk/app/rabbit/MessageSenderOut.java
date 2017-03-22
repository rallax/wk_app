package com.wk.app.rabbit;

import com.wk.app.message.DefaultMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author andrey.trotsenko
 */
@Component
public class MessageSenderOut extends MessageSender implements IMessageSender {
    @Value("${rabbitmq.queue.test_out}")
    private String queueTestNameOut;

    @Override
    public <T> void sendMessage(DefaultMessage<T> defaultMessage) {
        sendMessage(defaultMessage, queueTestNameOut);
    }
}
