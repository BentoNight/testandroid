package com.example.testtask.models.accounts;

import java.io.Serializable;

public class AccountData implements Serializable {

    public enum MyCurrency {
        RUB,
        USD,
        EUR,
        UNKNOWN
    }

    private final MyCurrency currency;
    private final float price;
    private String name;
    private final String description;

    public AccountData(String name, String description, MyCurrency currency, float price) {
        this.name = name;
        this.description = description;
        this.currency = currency;
        this.price = price;
    }

    public MyCurrency getCurrency() {
        return this.currency;
    }

    public float getPrice() {
        return this.price;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
