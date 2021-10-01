package com.example.demo.adapter.out.jpa;

import com.example.demo.adapter.web.dto.RateLimitJpaEntity;
import com.example.demo.domain.ExpirationZonedDate;
import com.example.demo.domain.entities.RateLimit;
import org.junit.jupiter.api.Test;

import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;

public class RateLimitJpaEntityTest {

    @Test
    public void testConversionWithBehindExpirationDate() throws Exception{
        RateLimit rateLimit = new RateLimit("1", POL_NUMBER(), ExpirationZonedDate.ofBehind(12));

        RateLimitJpaEntity rateLimitJpaEntity = RateLimitJpaEntity.of(rateLimit);

        assertThat(rateLimit)
                .isEqualTo(rateLimitJpaEntity.toRateLimit());
    }

    @Test
    public void testConversionWithAheadExpirationDate() throws Exception{
        RateLimit rateLimit = new RateLimit("1", POL_NUMBER(), ExpirationZonedDate.ofAhead(30));

        RateLimitJpaEntity rateLimitJpaEntity = RateLimitJpaEntity.of(rateLimit);

        assertThat(rateLimit)
                .isEqualTo(rateLimitJpaEntity.toRateLimit());
    }

}
