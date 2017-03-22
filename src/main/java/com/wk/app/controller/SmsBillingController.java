package com.wk.app.controller;


import com.google.gson.Gson;
import com.wk.app.facts.Sms;
import com.wk.app.facts.SmsBillingRecord;
import com.wk.app.message.DefaultMessageBuilder;
import com.wk.app.message.DefaultSmsBillingRecordMessage;
import com.wk.app.rabbit.IMessageSender;
import com.wk.app.service.SmsBillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;


/**
 * @author andrey.trotsenko
 */
@RestController
@RequestMapping("/billing")
public class SmsBillingController {
    Logger logger = LoggerFactory.getLogger(SmsBillingController.class);

    @Inject
    SmsBillingService smsBillingService;

    @Inject
    IMessageSender messageSenderIn;

    @Inject
    RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.test_out}")
    private String queueTestNameOut;


    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    //example {"sender":"43423423","receiver":"4545","time":"2017-03-21T13:47:30.757+0000"}
    public ResponseEntity<SmsBillingRecord> add(@RequestBody Sms sms) {
        try {
            return new ResponseEntity<>(smsBillingService.calculateAndSave(sms), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/rabitcalculate", method = RequestMethod.POST)
    //example {"sender":"43423423","receiver":"4545","time":"2017-03-21T13:47:30.757+0000"}
    public ResponseEntity<DefaultSmsBillingRecordMessage> rabbitCalculate(@RequestBody Sms sms) {
        try {
            messageSenderIn.sendMessage(DefaultMessageBuilder.buildDefaultMessage(sms));

            Thread.sleep(5000);

            Message m = rabbitTemplate.receive(queueTestNameOut);
            if(m == null)
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            DefaultSmsBillingRecordMessage defaultSmsBillingRecordMessage = new Gson().fromJson(new String(m.getBody()), DefaultSmsBillingRecordMessage.class);

            return new ResponseEntity<>(defaultSmsBillingRecordMessage, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
