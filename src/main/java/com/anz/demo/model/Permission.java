package com.anz.demo.model;

public enum Permission {
    VIEW_ACCOUNT("ViewAccount"),
    VIEW_TRANSACTION("ViewTransaction");

    Permission(final String value) {
        this.value = value;
    }

    private String value;
}
