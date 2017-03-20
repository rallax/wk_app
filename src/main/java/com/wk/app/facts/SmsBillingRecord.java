package com.wk.app.facts;

import java.math.BigDecimal;

/**
 * @author andrey.trotsenko
 */
public class SmsBillingRecord extends BillingRecord {
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
}
