package com.spring.commonlib.model.enums;

public enum Language {
    ARABIC("ARABIC"),
    ENGLISH("ENGLISH");

    private String value;
    Language(String value) {
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
