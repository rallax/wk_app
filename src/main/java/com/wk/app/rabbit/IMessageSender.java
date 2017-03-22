package com.wk.app.rabbit;

import com.wk.app.message.DefaultMessage;

/**
 * @author andrey.trotsenko
 */
public interface IMessageSender {
    <T> void sendMessage(DefaultMessage<T> defaultMessage);
}
