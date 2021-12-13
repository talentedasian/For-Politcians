package com.example.demo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class JwtExpiration {

    private final LocalDateTime expiration;

    private JwtExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public static JwtExpiration ofBehind(long minutesBehind) {
        return new JwtExpiration(LocalDateTime.now(ZoneId.of("GMT+8")).minusMinutes(minutesBehind));
    }

    public Instant created() {
        return expiration.toInstant(ZoneOffset.of("+8"));
    }

    public LocalDateTime expiration() {
        return LocalDateTime.now(ZoneId.of("GMT+8"));
    }

}
