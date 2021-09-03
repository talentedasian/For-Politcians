package com.example.demo.unit;

import com.example.demo.domain.entities.ExpirationDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpirationDateTest {

    @Test
    public void shouldReturn7AsDaysTillExpirationWhenExpirationDateIs7Days() {
        final long DAYS_TO_EXPIRE = 7;

        var expirationDate = new ExpirationDate(LocalDate.now());

        assertThat(expirationDate.expirationDate(DAYS_TO_EXPIRE))
                .isEqualTo(LocalDate.now().plusDays(DAYS_TO_EXPIRE));

        assertThat(expirationDate.isExpired(DAYS_TO_EXPIRE))
                .isFalse();

        assertThat(expirationDate.daysLeftTillExpiration(DAYS_TO_EXPIRE))
                .isEqualTo("7");
    }

    @Test
    public void shouldReturn20AsDaysTillExpirationWhenExpirationDateIs20DaysAhead() {
        final long DAYS_TO_EXPIRE = 20;

        var expirationDate = new ExpirationDate(LocalDate.now());

        assertThat(expirationDate.expirationDate(DAYS_TO_EXPIRE))
                .isEqualTo(LocalDate.now().plusDays(DAYS_TO_EXPIRE));

        assertThat(expirationDate.isExpired(DAYS_TO_EXPIRE))
                .isFalse();

        assertThat(expirationDate.daysLeftTillExpiration(DAYS_TO_EXPIRE))
                .isEqualTo("20");
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenDaysLeftTillExpirationIs0() {
        final long DAYS_TO_EXPIRE = 0;

        var expirationDate = new ExpirationDate(LocalDate.now());

        assertThrows(IllegalStateException.class, () -> expirationDate.daysLeftTillExpiration(DAYS_TO_EXPIRE));
    }

    @Test
    public void shouldNotBeExpiredIfDaysToExpireIs0AndDatePassedIsToday() {
        final long DAYS_TO_EXPIRE = 0;

        var expirationDate = new ExpirationDate(LocalDate.now());

        assertThat(expirationDate.isExpired(DAYS_TO_EXPIRE))
                .isTrue();
    }

    @Test
    public void shouldBeExpiredIfDaysToExpireIs1AndDatePassedIsToday() {
        final long DAYS_TO_EXPIRE = 1;

        var expirationDate = new ExpirationDate(LocalDate.now());

        assertThat(expirationDate.isExpired(DAYS_TO_EXPIRE))
                .isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = { 2 , 100 , 20 , 7 , 55 , 420 })
    public void shouldBeExpiredIfDaysToExpireIsGreaterThan1AndDatePassedIsToday(long DAYS_TO_EXPIRE) {
        var expirationDate = new ExpirationDate(LocalDate.now());

        assertThat(expirationDate.isExpired(DAYS_TO_EXPIRE))
                .isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = { -2 , -100 , -20 , -7 , -55 , -420 })
    public void shouldThrowIfArgumentsPassedIsANegativeNumber(long DAYS_TO_EXPIRE) {
        var expirationDate = new ExpirationDate(LocalDate.now());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> expirationDate.isExpired(DAYS_TO_EXPIRE));

        assertEquals("argument passed must not be a negative number", exception.getMessage());
    }

}
