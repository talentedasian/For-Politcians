package com.example.demo.baseClasses;

import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.entities.UserRateLimitService;
import com.example.demo.domain.politicians.PoliticianNumber;

public class FakeDomainService {

    public static UserRateLimitService unliRateService() {
        return new UserRateLimitService() {
            @Override
            public boolean isUserNotRateLimited(AccountNumber accountNumber, PoliticianNumber politicianNumber) {
                return true;
            }

            @Override
            public void rateLimitUser(AccountNumber userAccountNumber, PoliticianNumber polNumber) {}

            @Override
            public long daysLeftToRateForUser(AccountNumber accountNumber, PoliticianNumber politicianNumber) {
                return 0;
            }
        };
    }

}
