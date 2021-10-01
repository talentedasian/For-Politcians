package com.example.demo.domain;

import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.entities.UserRateLimitService;

import java.util.Optional;

public class DefaultRateLimitDomainService implements UserRateLimitService {

    private final RateLimitRepository rateLimitRepository;

    public DefaultRateLimitDomainService(RateLimitRepository rateLimitRepository) {
        this.rateLimitRepository = rateLimitRepository;
    }

    @Override
    public boolean isUserNotRateLimited(AccountNumber accountNumber, PoliticianNumber politicianNumber) {
        Optional<RateLimit> rateLimit = rateLimitRepository.findUsingIdAndPoliticianNumber(accountNumber.accountNumber(), politicianNumber);
        return rateLimit.isEmpty() || rateLimit.get().isNotRateLimited();
    }

    @Override
    public void rateLimitUser(AccountNumber userAccountNumber, PoliticianNumber polNumber) {
        Optional<RateLimit> rateLimit = rateLimitRepository.findUsingIdAndPoliticianNumber(userAccountNumber.accountNumber(), polNumber);
        if (rateLimit.isPresent()) rateLimitRepository.deleteUsingIdAndPoliticianNumber(userAccountNumber.accountNumber(), polNumber);
        rateLimitRepository.save(new RateLimit(userAccountNumber.accountNumber(), polNumber, ExpirationZonedDate.now()));
    }

    @Override
    public long daysLeftToRateForUser(AccountNumber accountNumber, PoliticianNumber politicianNumber) {
        Optional<RateLimit> rateLimit = rateLimitRepository.findUsingIdAndPoliticianNumber(accountNumber.accountNumber(), politicianNumber);
        if (rateLimit.isEmpty()) return 0;
        try {
            return rateLimit.get().daysLeftOfBeingRateLimited();
        } catch(IllegalStateException e) {
            return 0;
        }
    }
}
