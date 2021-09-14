package com.example.demo.domain.entities;

import org.springframework.util.Assert;

import java.time.LocalDate;

public record ExpirationDate(LocalDate dateCreated) {

    public LocalDate expirationDate(long daysToExpire) {
        checkForNegativeNumber(daysToExpire);
        return this.dateCreated.plusDays(daysToExpire);
    }

    public boolean isExpired(long daysBeforeExpiration) {
        checkForNegativeNumber(daysBeforeExpiration);
        return this.dateCreated == null || LocalDate.now().minusDays(daysBeforeExpiration).isAfter(dateCreated)
                || LocalDate.now().minusDays(daysBeforeExpiration).isEqual(dateCreated);
    }

    public String daysLeftTillExpiration(long daysLeftTillExpiration) {
        checkForNegativeNumber(daysLeftTillExpiration);
        Assert.state(!isExpired(daysLeftTillExpiration), "should be not be expired when getting days left till expiration");
        return String.valueOf(expirationDate(daysLeftTillExpiration).getDayOfYear() - dateCreated.getDayOfYear());
    }

    private void checkForNegativeNumber(long number) {
        Assert.state(!String.valueOf(number).contains("-"), "argument passed must not be a negative number");
    }

}
