package com.user.management.exceptions;

public class BusinessException extends RuntimeException {

    protected final String code;
    protected final String field;

    public BusinessException(String message, String code, String field) {
        super(message);
        this.code = code;
        this.field = field;
    }
}
