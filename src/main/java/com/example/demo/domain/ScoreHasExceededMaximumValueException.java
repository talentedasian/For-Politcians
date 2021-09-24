package com.example.demo.domain;

public class ScoreHasExceededMaximumValueException extends RuntimeException{

    public ScoreHasExceededMaximumValueException(String message) {
        super(message);
    }
}
