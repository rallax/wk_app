package com.wk.app.couchbase.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.wk.app.enums.Tariff;
import org.springframework.data.couchbase.core.mapping.Document;

/**
 * @author mikhail.pasenko
 */
@Document(expiry=0)
public class Customer {

    @Id
    private String number;
    @Field
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
