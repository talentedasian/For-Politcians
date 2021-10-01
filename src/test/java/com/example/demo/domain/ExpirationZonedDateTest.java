package com.example.demo.domain;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("Domain")
public class ExpirationZonedDateTest {

    final ZoneId PH_ZONE_ID = ZoneId.of("Asia/Manila");

    @Test
    public void shouldReturn7DaysAheadAsExpirationDateIfDateCreatedIsNow() {
        final long DAYS_TO_EXPIRE = 7;
        var expirationDate = ExpirationZonedDate.now();

        assertThat(expirationDate.expirationDate(DAYS_TO_EXPIRE))
                .isEqualTo(LocalDate.now(PH_ZONE_ID).plusDays(DAYS_TO_EXPIRE));
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenRetrievingDaysLeftToExpireIfExpired() throws Exception{
        long expiredDate = -2;
        var expirationDate = ExpirationZonedDate.of(expiredDate);

        assertThatThrownBy(() -> expirationDate.daysLeftTillExpiration(1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void shouldNotBeExpired() throws Exception{
        var expirationDate = ExpirationZonedDate.now();

        assertThat(expirationDate.isNotExpired(1))
                .isTrue();
    }

    @Test
    public void shouldReturn5AsDaysTillExpirationWithAWeekAsExpirationWhen2DaysHasPassed() throws Exception{
        final String DAYS_TO_EXPIRE = "5";

        long dateExpiresWithinAWeekAnd2DaysHasPassed = -2;
        var expirationDate = ExpirationZonedDate.of(dateExpiresWithinAWeekAnd2DaysHasPassed);

        assertThat(expirationDate.daysLeftTillExpiration(7))
                .isEqualTo(DAYS_TO_EXPIRE);
    }

    @Test
    public void ifCurrentDateIsAfterExpirationDateThenIsNotExpired() throws Exception{
        var expirationDate = ExpirationZonedDate.now();

        assertThat(expirationDate.isExpired(7))
                .isFalse();
    }

    @Test
    public void shouldReturnExpectedDaysLeftTillRatingWithExpirationDateBeingNextYear() throws Exception{
        long daysToJanuary1FromNovember31 = 61;
        long daysToNovember1FromNow = 31;
        var expirationDate = ExpirationZonedDate.of(daysToNovember1FromNow);

        assertThat(expirationDate.daysLeftTillExpiration(daysToJanuary1FromNovember31))
                .isEqualTo(String.valueOf(daysToJanuary1FromNovember31 + daysToNovember1FromNow));
    }

}
