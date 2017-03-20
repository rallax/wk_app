package com.wk.app.service;

import com.wk.app.facts.Customer;

/**
 * @author andrey.trotsenko
 */
public interface CustomerService {

    void addCustomer(Customer customer);

    Customer getCustomerByNumber(String number);
}
