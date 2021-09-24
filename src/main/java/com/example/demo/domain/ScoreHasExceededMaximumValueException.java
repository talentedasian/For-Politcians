package com.example.demo.domain;

public class ScoreHasExceededMaximumValueException extends RuntimeException{

    private static final String defaultMessage = "Score has exceeded maximum value which is 10";

    public ScoreHasExceededMaximumValueException() {
        super(defaultMessage);
    }

    public ScoreHasExceededMaximumValueException(Throwable cause) {
        super(defaultMessage, cause);
    }

}
