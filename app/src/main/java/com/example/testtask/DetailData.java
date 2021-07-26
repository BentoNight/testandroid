package com.example.testtask;

public class DetailData {

    public enum MyCurrency {
        USD,
        RUB,
        EUR,
        UNKNOWN
    }

    private final MyCurrency currency;
    private final float sum;

    public DetailData(MyCurrency currency, float sum) {
        this.currency = currency;
        this.sum = sum;
    }

    public MyCurrency getCurrency() {
        return this.currency;
    }

    public float getSum() {
        return this.sum;
    }
}
