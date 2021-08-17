package com.example.demo.domain.entities;

import org.springframework.util.Assert;

import java.time.LocalDate;

public record ExpirationDate(LocalDate dateCreated) {

    static final int DAYS_TILL_EXPIRATION = 7;

    public LocalDate expirationDate() {
        return this.dateCreated.plusDays(DAYS_TILL_EXPIRATION);
    }

    public boolean isNotRateLimited() {
        return this.dateCreated == null || LocalDate.now().minusDays(DAYS_TILL_EXPIRATION).isBefore(dateCreated);
    }

    public String daysLeftTillRateLimited() {
        Assert.state(isNotRateLimited(), "should not be rate limited");
        return String.valueOf(dateCreated.getDayOfMonth() - LocalDate.now().minusDays(DAYS_TILL_EXPIRATION).getDayOfMonth());
    }

}
