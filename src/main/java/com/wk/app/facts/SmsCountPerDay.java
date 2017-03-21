package com.wk.app.facts;

import java.time.LocalDate;

/**
 * @author andrey.trotsenko
 */
public class SmsCountPerDay {
    private String number;
    private int count;
    private LocalDate date;

    public SmsCountPerDay() {
    }

    public SmsCountPerDay(String number, int count, LocalDate date) {
        this.number = number;
        this.count = count;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
