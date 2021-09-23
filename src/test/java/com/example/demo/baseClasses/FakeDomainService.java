package com.example.demo.baseClasses;

import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.entities.UserRateLimitService;
import com.example.demo.domain.entities.PoliticianNumber;

import static com.example.demo.baseClasses.FakeDomainService.StubRateLimitService.cantRate;
import static com.example.demo.baseClasses.FakeDomainService.StubRateLimitService.unli;

public class FakeDomainService {

    public static UserRateLimitService unliRateService() {
        return unli();
    }

    public static UserRateLimitService noRateService() {
        return cantRate();
    }

    public static class StubRateLimitService implements UserRateLimitService {

        boolean isUnli;

        public StubRateLimitService(boolean isUnli) {
            this.isUnli = isUnli;
        }

        public static UserRateLimitService unli() {
            return new StubRateLimitService(true);
        }

        public static UserRateLimitService cantRate() {
            return new StubRateLimitService(false);
        }

        @Override
        public boolean isUserNotRateLimited(AccountNumber accountNumber, PoliticianNumber politicianNumber) {
            return isUnli;
        }

        @Override
        public void rateLimitUser(AccountNumber userAccountNumber, PoliticianNumber polNumber) {
        }

        @Override
        public long daysLeftToRateForUser(AccountNumber accountNumber, PoliticianNumber politicianNumber) {
            return isUnli ? 0 : 7;
        }

    }

}
