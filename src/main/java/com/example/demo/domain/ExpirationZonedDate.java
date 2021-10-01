package com.example.demo.domain;

import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

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

    /**
     *  Takes in how many days ahead or behind to use for
     *  the creation date.
     *
     * @param daysAheadOrBehindFromNow days ahead or behind from current day.
     * @return ExpirationZonedDate that is days ahead or behind today as date created.
     */
    public static ExpirationZonedDate of(long daysAheadOrBehindFromNow) {
        LocalDate now = LocalDate.now(zoneId);
        return new ExpirationZonedDate(now.plusDays(daysAheadOrBehindFromNow));
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

}
