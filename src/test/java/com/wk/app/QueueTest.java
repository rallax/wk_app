package com.wk.app;

import com.wk.config.RabbitConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;

/**
 * @author Roman Luzko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RabbitConfiguration.class})
public class QueueTest {
    @Value("${rabbitmq.queue.test}")
    private String queueTestName;

    @Inject
    RabbitTemplate rabbitTemplate;


    @Test
    public void shouldQueueConnection() {
        rabbitTemplate.convertAndSend(queueTestName, "TestMessage1");
        assertTrue(true);
    }


}
