package com.example.demo.domain;

public class NotLastPageException extends RuntimeException {

    public NotLastPageException() {
    }

    public NotLastPageException(String message) {
        super(message);
    }

    public NotLastPageException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotLastPageException(Throwable cause) {
        super(cause);
    }

    public NotLastPageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
