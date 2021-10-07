package com.example.demo.domain;

public class NotLastPageException extends RuntimeException {

    public NotLastPageException(String message) {
        super(message);
    }

}
