package com.anz.demo.model;

public enum AccountType {
    SAVINGS("Savings"),
    CURRENT("Current");

    AccountType(final String value) {
        this.value = value;
    }

    private String value;
}
