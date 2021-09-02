package com.example.demo.exceptions;

public class PoliticianNotPersistableException extends Exception{

    public PoliticianNotPersistableException() {
    }

    public PoliticianNotPersistableException(String message) {
        super(message);
    }

    public PoliticianNotPersistableException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoliticianNotPersistableException(Throwable cause) {
        super(cause);
    }

    public PoliticianNotPersistableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
