package com.example.demo.adapter.web;

public class NoSessionFoundException extends RuntimeException{

    public NoSessionFoundException() {
    }

    public NoSessionFoundException(String message) {
        super(message);
    }

    public NoSessionFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSessionFoundException(Throwable cause) {
        super(cause);
    }

    public NoSessionFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
