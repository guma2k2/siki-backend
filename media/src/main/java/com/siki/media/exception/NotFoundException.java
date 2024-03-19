package com.siki.media.exception;


import com.siki.media.utils.MessageUtils;

public class NotFoundException extends RuntimeException{
    private String message;

    public NotFoundException(String errorCode, Object ...var2) {
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
