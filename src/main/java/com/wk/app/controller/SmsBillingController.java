package com.wk.app.controller;


import com.wk.app.facts.Sms;
import com.wk.app.facts.SmsBillingRecord;
import com.wk.app.service.SmsBillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    //example {"id":1,"sender":"43423423","receiver":"4545","local":false,"date":[2017,3,17]}
    public ResponseEntity<SmsBillingRecord> add(@RequestBody Sms sms) {
        try {
            return new ResponseEntity<>(smsBillingService.calculate(sms), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
