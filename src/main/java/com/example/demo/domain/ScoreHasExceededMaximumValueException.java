package com.example.demo.domain;

public class ScoreHasExceededMaximumValueException extends RuntimeException{

    public ScoreHasExceededMaximumValueException() {
        super("Score has exceeded maximum value which is 10");
    }

    public ScoreHasExceededMaximumValueException(String message) {
        super(message);
    }
}
