package com.user.management.exceptions;

import com.user.management.config.translate.BundleTranslator;
import lombok.Getter;

@Getter
public class FieldException extends RuntimeException {

    protected final String code;
    protected final String field;

    public FieldException(String message, String code, String field) {
        super(message);
        this.code = code;
        this.field = field;
    }
}
