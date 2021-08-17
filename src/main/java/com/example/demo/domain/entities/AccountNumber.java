package com.example.demo.domain.entities;

import com.example.demo.domain.userRaterNumber.AbstractUserRaterNumber;

public record AccountNumber(String id, AbstractUserRaterNumber accountNumberCalculator) {

    public AccountNumber(String id, AbstractUserRaterNumber accountNumberCalculator) {
        this.accountNumberCalculator = accountNumberCalculator;
        this.id = accountNumberCalculator.calculateEntityNumber().getAccountNumber();
    }

    public String retrieveAccountNumber() {
        return this.id();
    }

}
