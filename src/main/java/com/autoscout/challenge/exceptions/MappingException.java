package com.autoscout.challenge.exceptions;

public class MappingException extends Exception {

    private final String message;
    private final Exception exception;

    public MappingException(String message, Exception exception) {
        this.message = message;
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
