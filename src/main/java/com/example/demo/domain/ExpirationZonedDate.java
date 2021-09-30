package com.example.demo.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class ExpirationZonedDate {

    private final LocalDate dateCreated;
    private final ZoneId zoneId;

    private ExpirationZonedDate(ZonedDateTime dateCreated) {
        ZoneId zone = dateCreated.getZone();
        this.dateCreated = dateCreated.toLocalDate();
        this.zoneId = zone;
    }

    public static ExpirationZonedDate of(ZonedDateTime dateCreated) {
        return new ExpirationZonedDate(dateCreated);
    }

    public LocalDate expirationDate(long daysToExpire) {
        return this.dateCreated.plusDays(daysToExpire);
    }

    public String daysLeftTillExpiration(long daysToExpire) {
        return String.valueOf(ChronoUnit.DAYS.between(ZonedDateTime.now(zoneId).toLocalDate(), expirationDate(daysToExpire)));
    }

    public boolean isExpired(long daysToExpire) {
        return expirationDate(daysToExpire).isEqual(ZonedDateTime.now(zoneId).toLocalDate()) ||
                ZonedDateTime.now(zoneId).toLocalDate().minusDays(daysToExpire).isAfter(expirationDate(daysToExpire));
    }

}
