package com.wk.app.facts;

import java.math.BigDecimal;

/**
 * @author andrey.trotsenko
 */
public abstract class BillingRecord {
    protected Customer customer;
    protected BigDecimal price;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
