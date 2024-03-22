package com.siki.product.exception;

import com.siki.product.utils.MessageUtils;

public class DuplicatedException extends RuntimeException{
    private String message;
    public DuplicatedException(String errorCode, Object ...var2) {
        this.message = MessageUtils.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
