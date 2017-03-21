package com.wk.app.service;

import com.wk.app.couchbase.repository.CustomerRepository;
import com.wk.app.facts.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author andrey.trotsenko
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Inject
    CustomerRepository customerRepository;
//    private HashMap<String, Customer> customers = new HashMap<>();

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(new com.wk.app.couchbase.model.Customer(customer.getNumber(), customer.getTariff()));
    }

    @Override
    public Customer getCustomerByNumber(String number) {
        com.wk.app.couchbase.model.Customer one = customerRepository.findOne(number);
        return new Customer(one.getNumber(), one.getTariff());
    }
}
