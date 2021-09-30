package com.example.demo.domain.entities;

import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public record ExpirationDate(LocalDate dateCreated) {

    public ExpirationDate(LocalDate dateCreated) {
        this.dateCreated = LocalDate.ofInstant(dateCreated.atStartOfDay().toInstant(ZoneOffset.of("+8")), ZoneId.of("GMT+8"));
    }

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
        return daysToExpirationDate(daysLeftTillExpiration);
    }

    private String daysToExpirationDate(long daysLeftTillExpiration) {
        return String.valueOf(ChronoUnit.DAYS.between(LocalDate.now(ZoneId.of("GMT+8")), expirationDate(daysLeftTillExpiration)));
    }

    private void checkForNegativeNumber(long number) {
        Assert.state(!String.valueOf(number).contains("-"), "argument passed must not be a negative number");
    }

}
