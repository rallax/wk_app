package com.wk.app.tests;

import com.google.gson.Gson;
import com.wk.app.config.RabbitTestConfiguration;
import com.wk.app.enums.Tariff;
import com.wk.app.facts.Customer;
import com.wk.app.facts.Sms;
import com.wk.app.message.DefaultMessageBuilder;
import com.wk.app.message.DefaultSmsBillingRecordMessage;
import com.wk.app.rabbit.IMessageSender;
import com.wk.app.queue.RabbitMqInListener;
import com.wk.app.rabbit.MessageSenderIn;
import com.wk.app.rabbit.MessageSenderOut;
import com.wk.app.service.CustomerService;
import com.wk.app.service.SmsBillingService;
import com.wk.app.service.SmsBillingServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.mockito.Mockito;
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
 * @author andrey.trotsenko
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SmsBillingServiceWithRabbitTest.SmsBillingWithRabbitConf.class, RabbitTestConfiguration.class})
public class SmsBillingServiceWithRabbitTest {
    private static final String NUMBER = "22233";
    private static final BigDecimal TARIFF_SIMPLE_PRICE = new BigDecimal(2);

    @Configuration
    public static class SmsBillingWithRabbitConf {
        @Bean
        public SmsBillingService smsBillingService() {
            return new SmsBillingServiceImpl();
        }

        @Bean
        public CustomerService customerService() {
            return Mockito.mock(CustomerService.class);
        }

        @Bean
        public RabbitMqInListener rabbitMqInListener() {
            return new RabbitMqInListener();
        }

        @Bean
        public KieContainer kieContainer() {
            return KieServices.Factory.get().getKieClasspathContainer();
        }

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
    IMessageSender messageSenderIn;

    @Inject
    CustomerService customerService;

    @Inject
    RabbitTemplate rabbitTemplate;

    @Inject
    Queue queueTestOut;


    @Test
    public void calculateGoodPrice() throws InterruptedException {
        mockCustomer(Tariff.SIMPLE);
        messageSenderIn.sendMessage(DefaultMessageBuilder.buildDefaultMessage(createDefaultSms()));

        Thread.sleep(5000);

        Message m = rabbitTemplate.receive(queueTestOut.getName());

        assertNotNull(m);

        //noinspection unchecked
        DefaultSmsBillingRecordMessage defaultSmsBillingRecordMessage = new Gson().fromJson(new String(m.getBody()), DefaultSmsBillingRecordMessage.class);
        assertEquals(TARIFF_SIMPLE_PRICE, defaultSmsBillingRecordMessage.getBody().getPrice());
    }

    @Test
    public void calculateBadPrice() throws InterruptedException {
        mockBadCustomer();
        messageSenderIn.sendMessage(DefaultMessageBuilder.buildDefaultMessage(createDefaultSms()));

        Thread.sleep(5000);

        Message m = rabbitTemplate.receive(queueTestOut.getName());

        assertNotNull(m);

        //noinspection unchecked
        DefaultSmsBillingRecordMessage defaultSmsBillingRecordMessage = new Gson().fromJson(new String(m.getBody()), DefaultSmsBillingRecordMessage.class);
        assertNotNull(defaultSmsBillingRecordMessage.getErrorDescription());
        assertNotNull(defaultSmsBillingRecordMessage.getErrorDescription().getUserMessage());
    }

    private static Sms createDefaultSms() {
        return new Sms(NUMBER, "2222211133", true, new Date());
    }

    private void mockCustomer(Tariff tariff) {
        Customer customer = new Customer(NUMBER, tariff);
        Mockito.doReturn(customer).when(customerService).getCustomerByNumber(NUMBER);
    }

    private void mockBadCustomer() {
        Mockito.doThrow(new NullPointerException("Customer is null")).when(customerService).getCustomerByNumber(NUMBER);
    }
}
