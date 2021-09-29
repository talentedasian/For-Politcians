package com.example.demo.domain.collaboration;

import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.domain.DefaultRateLimitDomainService;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.RateLimit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;

import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("Domain")
@ExtendWith(MockitoExtension.class)
public class DefaultRateLimitServiceTest {

    RateLimitRepository rateLimitRepo;

    @BeforeEach
    public void setup() {
        rateLimitRepo = new InMemoryRateLimitRepository();
    }

    @Test
    public void shouldDeleteExistingRateLimitWhenRateLimitingUser() throws Exception{
        LocalDate expiredDate = LocalDate.now(ZoneId.of("GMT+8")).minusDays(8);
        String ACCOUNT_NUMBER = ACC_NUMBER().accountNumber();
        PoliticianNumber POLITICIAN_NUMBER = POL_NUMBER();

        rateLimitRepo.save(new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, expiredDate));

        var domainService = new DefaultRateLimitDomainService(rateLimitRepo);
        domainService.rateLimitUser(AccountNumber.of(ACCOUNT_NUMBER), POLITICIAN_NUMBER);

        assertThat(rateLimitRepo.countUsingIdAndPoliticianNumber(ACCOUNT_NUMBER, POLITICIAN_NUMBER))
                .isEqualTo(1);
    }

    @Test
    public void userShouldNotBeRateLimitedIfRateLimitIsNotOnRepository() throws Exception{
        var domainService = new DefaultRateLimitDomainService(rateLimitRepo);
        boolean isNotRateLimited = domainService.isUserNotRateLimited(ACC_NUMBER(), POL_NUMBER());

        assertThat(isNotRateLimited)
                .isTrue();
    }

    @Test
    public void shouldThrow0DaysLeftToRateWhenUserIsNotRateLimitedButHasExistingRateLimitOnPolitician() throws Exception{
        LocalDate expiredDate = LocalDate.now(ZoneId.of("GMT+8")).minusDays(10);
        String ACCOUNT_NUMBER = ACC_NUMBER().accountNumber();
        PoliticianNumber POLITICIAN_NUMBER = POL_NUMBER();

        rateLimitRepo.save(new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, expiredDate));

        var domainService = new DefaultRateLimitDomainService(rateLimitRepo);
        long daysLeftToRate = domainService.daysLeftToRateForUser(AccountNumber.of(ACCOUNT_NUMBER), POLITICIAN_NUMBER);

        assertThat(daysLeftToRate)
                .isEqualTo(0);

    }

    @Test
    public void shouldReturnExpectedDaysLeftToRateWhenUserHasExistingAndNonExpiredRateLimitOnPolitician() throws Exception{
        long daysTillRateLimitExpiration = 5;
        long fiveDaysLeftInAWeek = RateLimit.RATE_LIMIT - daysTillRateLimitExpiration;
        LocalDate expiredDate = LocalDate.now(ZoneId.of("GMT+8")).minusDays(fiveDaysLeftInAWeek);
        String ACCOUNT_NUMBER = ACC_NUMBER().accountNumber();
        PoliticianNumber POLITICIAN_NUMBER = POL_NUMBER();

        rateLimitRepo.save(new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, expiredDate));

        var domainService = new DefaultRateLimitDomainService(rateLimitRepo);
        long daysLeftToRate = domainService.daysLeftToRateForUser(AccountNumber.of(ACCOUNT_NUMBER), POLITICIAN_NUMBER);

        assertThat(daysLeftToRate)
                .isEqualTo(daysTillRateLimitExpiration);
    }

}
