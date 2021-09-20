package com.example.demo.adapter.in.web;

public class InappropriateAccountNumberException extends RuntimeException {
    public InappropriateAccountNumberException(String accNumber) {
        super(String.format("Account number %s is invalid", accNumber));
    }
}
