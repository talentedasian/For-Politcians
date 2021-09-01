package com.example.demo.domain.entities;

import org.springframework.util.Assert;

import java.time.LocalDate;

public record ExpirationDate(LocalDate dateCreated) {

    public LocalDate expirationDate(long daysToExpire) {
        return this.dateCreated.plusDays(daysToExpire);
    }

    public boolean isExpired(long daysBeforeExpiration) {
        return this.dateCreated == null || LocalDate.now().minusDays(daysBeforeExpiration).isAfter(dateCreated);
    }

    public String daysLeftTillExpiration(long daysLeftTillExpiration) {
        Assert.state(!isExpired(daysLeftTillExpiration), "should not be expired");
        return String.valueOf(dateCreated.getDayOfMonth() - LocalDate.now().minusDays(daysLeftTillExpiration).getDayOfMonth());
    }

}
