package com.wk.app.tests;

import com.google.gson.Gson;
import com.wk.app.config.RabbitTestConfiguration;
import com.wk.app.enums.Tariff;
import com.wk.app.facts.Customer;
import com.wk.app.facts.Sms;
import com.wk.app.facts.SmsBillingRecord;
import com.wk.app.message.DefaultMessageBuilder;
import com.wk.app.message.DefaultSmsBillingRecordMessage;
import com.wk.app.message.DefaultSmsMessage;
import com.wk.app.rabbit.IMessageSender;
import com.wk.app.rabbit.MessageSenderIn;
import com.wk.app.rabbit.MessageSenderOut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Roman Luzko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {QueueTest.QueueConf.class, RabbitTestConfiguration.class})
public class QueueTest {
    private static final String NUMBER = "11112222";

    @Configuration
    public static class QueueConf {
        @Bean
        public IMessageSender messageSenderIn() {
            return new MessageSenderIn();
        }

        @Bean
        public IMessageSender messageSenderOut() {
            return new MessageSenderOut();
        }
    }

    @Inject
    Queue queueTestOut;

    @Inject
    Queue queueTestIn;

    @Inject
    IMessageSender messageSenderIn;

    @Inject
    IMessageSender messageSenderOut;

    @Inject
    RabbitTemplate rabbitTemplate;


    @Test
    public void sendMessageIn() throws InterruptedException {
        messageSenderIn.sendMessage(DefaultMessageBuilder.buildDefaultMessage(createDefaultSms()));

        Thread.sleep(5000);

        Message m = rabbitTemplate.receive(queueTestIn.getName());

        assertNotNull(m);

        DefaultSmsMessage defaultMessage = new Gson().fromJson(new String(m.getBody()), DefaultSmsMessage.class);
        assertNotNull(defaultMessage);
        assertEquals(NUMBER, defaultMessage.getBody().getSender());
    }

    @Test
    public void sendMessageOut() throws InterruptedException {
        messageSenderOut.sendMessage(DefaultMessageBuilder.buildDefaultMessage(createDefaultSmsBillingRecord()));

        Thread.sleep(5000);

        Message m = rabbitTemplate.receive(queueTestOut.getName());

        assertNotNull(m);

        DefaultSmsBillingRecordMessage defaultMessage = new Gson().fromJson(new String(m.getBody()), DefaultSmsBillingRecordMessage.class);
        assertNotNull(defaultMessage);
        assertEquals(NUMBER, defaultMessage.getBody().getCustomer().getNumber());
    }

    private static SmsBillingRecord createDefaultSmsBillingRecord() {
        final String number = "11112222";
        Customer customer = new Customer(number, Tariff.SIMPLE);
        return new SmsBillingRecord(customer, new BigDecimal(2), createDefaultSms());
    }

    private static Sms createDefaultSms() {
        return new Sms(NUMBER, "2222211133", true, new Date());
    }


}
