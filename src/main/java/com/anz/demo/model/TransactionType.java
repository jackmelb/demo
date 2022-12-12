package com.anz.demo.model;

public enum TransactionType {
    CREDIT("Credit"),
    DEBIT("Debit");

    TransactionType(final String value) {
        this.value = value;
    }

    private String value;
}
