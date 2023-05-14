package com.user.management.config;

import com.user.management.config.translate.BundleTranslator;
import com.user.management.exceptions.BadAuthException;
import com.user.management.exceptions.FieldException;
import com.user.management.model.exception.ErrorExceptionApi;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(FieldException.class)
    public final ResponseEntity<Object> handleFieldException(FieldException exception) {
        return buildResponseEntity(new ErrorExceptionApi(HttpStatus.BAD_REQUEST,BundleTranslator.getMessages(exception.getMessage())));
    }

    @ExceptionHandler(BadAuthException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(BadAuthException exception) {
        return buildResponseEntity(new ErrorExceptionApi(HttpStatus.UNAUTHORIZED, BundleTranslator.getMessages(exception.getMessage())));
    }


    private ResponseEntity<Object> buildResponseEntity(ErrorExceptionApi errorExceptionApi){
        return new ResponseEntity<Object>(errorExceptionApi,errorExceptionApi.getStatus());
    }
}
