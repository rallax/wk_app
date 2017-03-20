package com.wk.app.couchbase.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by mikhail.pasenko on 20.03.2017.
 */
@Document(expiry=0)
public class Sms {

    @Id
    private long id;
    @Field
    private String sender;
    @Field
    private String receiver;
    @Field
    private boolean local = true;
    @Field
    private Date date;
    @Field
    private int nppPerDay;

    public Sms() {
    }

    public Sms(long id, String sender, String receiver, boolean local, Date date, int nppPerDay) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.local = local;
        this.date = date;
        this.nppPerDay = nppPerDay;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNppPerDay() {
        return nppPerDay;
    }

    public void setNppPerDay(int nppPerDay) {
        this.nppPerDay = nppPerDay;
    }


}
