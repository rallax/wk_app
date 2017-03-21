package com.wk.app.facts;

import java.math.BigDecimal;

/**
 * @author andrey.trotsenko
 */
public class SmsBillingRecord  {
    private Customer customer;
    private BigDecimal price;
    private Sms sms;

    public SmsBillingRecord() {
    }

    public SmsBillingRecord(Customer customer, BigDecimal price, Sms sms) {
        this.sms = sms;
        this.customer = customer;
        this.price = price;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

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
