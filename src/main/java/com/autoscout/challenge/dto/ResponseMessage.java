package com.autoscout.challenge.dto;

import java.io.Serializable;

public class ResponseMessage implements Serializable {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
