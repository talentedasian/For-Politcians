package com.example.demo.domain;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("Domain")
public class ExpirationZonedDateTest {

    final ZoneId PH_ZONE_ID = ZoneId.of("Asia/Manila");

    @Test
    public void shouldReturn7DaysAheadAsExpirationDate() {
        final long DAYS_TO_EXPIRE = 7;
        var expirationDate = ExpirationZonedDate.of(ZonedDateTime.now(PH_ZONE_ID));

        assertThat(expirationDate.expirationDate(DAYS_TO_EXPIRE))
                .isEqualTo(LocalDate.now(PH_ZONE_ID).plusDays(DAYS_TO_EXPIRE));
    }

    @Test
    public void shouldReturn5AsDaysTillExpirationWithAWeekAsExpirationWhen2DaysHasPassed() throws Exception{
        final String DAYS_TO_EXPIRE = "5";

        ZonedDateTime dateExpiresWithinAWeekAnd2DaysHasPassed = ZonedDateTime.now(PH_ZONE_ID).minusDays(2);
        var expirationDate = ExpirationZonedDate.of(dateExpiresWithinAWeekAnd2DaysHasPassed);

        assertThat(expirationDate.daysLeftTillExpiration(7))
                .isEqualTo(DAYS_TO_EXPIRE);
    }

    @Test
    public void ifCurrentDateIsAfterExpirationDateThenIsNotExpired() throws Exception{
        ZonedDateTime dateCreated = ZonedDateTime.now(PH_ZONE_ID);
        var expirationDate = ExpirationZonedDate.of(dateCreated);

        assertThat(expirationDate.isExpired(7))
                .isFalse();
    }

}
