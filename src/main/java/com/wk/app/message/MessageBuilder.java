package com.wk.app.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author andrey.trotsenko
 */
public class MessageBuilder {
    public static final String MESSAGE_VERSION = "1.0";
    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    private final MessageProperties mp;
    private StringBuilder body;

    public MessageBuilder(Date eventTime) {
        mp = new MessageProperties();
        mp.setContentType(DEFAULT_CONTENT_TYPE);
        mp.setHeader("version", MESSAGE_VERSION);
        mp.setHeader("eventDate", format(eventTime));

        body = new StringBuilder();
    }

    public MessageBuilder() {
        this(Calendar.getInstance().getTime());
    }

    public MessageBuilder setContentType(String mimeTtype) {
        mp.setContentType(mimeTtype);
        return this;
    }

    public MessageBuilder appendToBody(String bodyPart) {
        this.body.append(bodyPart);
        return this;
    }

    public MessageBuilder setBody(String body) {
        this.body = new StringBuilder(body);
        return this;
    }

    public Message build() {
        return new Message(body.toString().getBytes(), mp);
    }

    private String format(Date time) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").format(time);
    }
}
