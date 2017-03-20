package com.wk.app.service;

import com.wk.app.facts.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author andrey.trotsenko
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private HashMap<String, Customer> customers = new HashMap<>();

    @Override
    public void addCustomer(Customer customer) {
        customers.putIfAbsent(customer.getNumber(), customer);
    }

    @Override
    public Customer getCustomerByNumber(String number) {
        return customers.get(number);
    }
}
