package com.wk.app.facts;

import com.wk.app.enums.Tariff;

/**
 * @author andrey.trotsenko
 */
public class Customer {
    private String number;
    private Tariff tariff;

    public Customer() {
    }

    public Customer(String number, Tariff tariff) {
        this.number = number;
        this.tariff = tariff;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }
}
