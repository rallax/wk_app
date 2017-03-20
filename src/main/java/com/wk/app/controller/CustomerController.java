package com.wk.app.controller;

import com.google.gson.Gson;
import com.wk.app.facts.Customer;
import com.wk.app.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author andrey.trotsenko
 */

@RestController
@RequestMapping("/customer")
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Inject
    CustomerService customerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    //example {"number":"43423423","tariff":"SIMPLE"}
    public ResponseEntity<String> add(@RequestBody Customer customer) {
        try {
            customerService.addCustomer(customer);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new Gson().toJson(customer), HttpStatus.OK);
    }

}
