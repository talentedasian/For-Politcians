package com.example.demo.domain;

public class JSONWebTokenException extends RuntimeException{

    public JSONWebTokenException(String message) {
        super(message);
    }

    public JSONWebTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONWebTokenException(Throwable cause) {
        super(cause);
    }

    public JSONWebTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
