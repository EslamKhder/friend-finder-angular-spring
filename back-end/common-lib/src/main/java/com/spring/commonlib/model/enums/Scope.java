package com.spring.commonlib.model.enums;

public enum Scope {
    USER("USER"),
    ORGANIZATION("ORGANIZATION");

    private String value;
    Scope(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value();
    }

}
