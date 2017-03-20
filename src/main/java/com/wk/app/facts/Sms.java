package com.wk.app.facts;


import java.time.LocalDate;

/**
 * @author andrey.trotsenko
 */
public class Sms {
    private Long id;
    private String sender;
    private String receiver;
    private boolean local = true;
    private LocalDate date;
    private int nppPerDay;

    public Sms() {
    }

    public Sms(Long id, String sender, String receiver, boolean local, LocalDate date) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.local = local;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public int getNppPerDay() {
        return nppPerDay;
    }

    public void setNppPerDay(int nppPerDay) {
        this.nppPerDay = nppPerDay;
    }
}
