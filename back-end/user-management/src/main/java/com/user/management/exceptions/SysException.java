package com.user.management.exceptions;

public class SysException extends RuntimeException {

    protected final String code;

    public SysException(String message, String code) {
        super(message);
        this.code = code;
    }
}
