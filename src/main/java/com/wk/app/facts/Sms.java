package com.wk.app.facts;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author andrey.trotsenko
 */
public class Sms {
    private String sender;
    private String receiver;
    private boolean local = true;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date time;

    public Sms() {
    }

    public Sms(String sender, String receiver, boolean local, Date time) {
        this.sender = sender;
        this.receiver = receiver;
        this.local = local;
        this.time = time;
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

    public LocalDate getDate() {
        if(time == null)
            return null;

        return time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
