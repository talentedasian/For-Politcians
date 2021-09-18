package com.example.demo.domain.entities;

public interface UserRateLimitService {

    boolean isUserNotRateLimited(AccountNumber accountNumber, PoliticianNumber politicianNumber);

    void rateLimitUser(AccountNumber userAccountNumber, PoliticianNumber polNumber);

    long daysLeftToRateForUser(AccountNumber accountNumber, PoliticianNumber politicianNumber);
}
