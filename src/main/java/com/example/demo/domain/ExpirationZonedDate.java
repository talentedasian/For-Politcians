package com.example.demo.domain;

import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Handles expirations in GMT+8.
 */
public class ExpirationZonedDate {

    private final LocalDate dateCreated;
    public static final ZoneId zoneId = ZoneId.of("GMT+8");

    private ExpirationZonedDate(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate dateCreated() {
        return this.dateCreated;
    }

    public static ExpirationZonedDate ofAhead(long daysAhead) {
        throwIllegalStateExceptionIfDaysNegative(daysAhead);
        return new ExpirationZonedDate(LocalDate.now(zoneId).plusDays(daysAhead));
    }

    public static ExpirationZonedDate ofBehind(long daysBehind) {
        throwIllegalStateExceptionIfDaysNegative(daysBehind);
        return new ExpirationZonedDate(LocalDate.now(zoneId).minusDays(daysBehind));
    }

    private static void throwIllegalStateExceptionIfDaysNegative(long days) {
        Assert.state(days >= 0, "Days cannot be negative");
    }

    /**
     *
     * @return ExpirationZonedDate with the date created as today
     */
    public static ExpirationZonedDate now() {
        LocalDate now = LocalDate.now(zoneId);
        return new ExpirationZonedDate(now);
    }

    public LocalDate expirationDate(long daysToExpire) {
        return this.dateCreated.plusDays(daysToExpire);
    }

    public String daysLeftTillExpiration(long daysToExpire) {
        Assert.state(isNotExpired(daysToExpire), "Expiration date should not be expired when retrieving days left till expiration");
        return String.valueOf(ChronoUnit.DAYS.between(ZonedDateTime.now(zoneId).toLocalDate(), expirationDate(daysToExpire)));
    }

    public boolean isExpired(long daysToExpire) {
        return expirationDate(daysToExpire).isEqual(LocalDate.now(zoneId)) ||
                LocalDate.now(zoneId).isAfter(expirationDate(daysToExpire));
    }

    public boolean isNotExpired(long daysToExpire) {
        return !isExpired(daysToExpire);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpirationZonedDate that = (ExpirationZonedDate) o;

        return Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return dateCreated != null ? dateCreated.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ExpirationZonedDate dateCreated= " + dateCreated;
    }
}
