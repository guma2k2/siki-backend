package com.siki.product.exception;

import com.siki.product.utils.MessageUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class DuplicatedException extends RuntimeException{
    private String message;
    public DuplicatedException(String errorCode, Object ...var2) {
        this.message = MessageUtils.getMessage(errorCode, var2);
    }

    public DuplicatedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
