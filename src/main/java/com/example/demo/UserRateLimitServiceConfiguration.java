package com.example.demo;

import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.UserRateLimitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRateLimitServiceConfiguration {
    @Bean
    public UserRateLimitService rateLimitService() {
        return new UserRateLimitService() {
            @Override
            public boolean isUserNotRateLimited(AccountNumber accountNumber, PoliticianNumber politicianNumber) {
                return true;
            }

            @Override
            public void rateLimitUser(AccountNumber userAccountNumber, PoliticianNumber polNumber) {

            }

            @Override
            public long daysLeftToRateForUser(AccountNumber accountNumber, PoliticianNumber politicianNumber) {
                return 0;
            }
        };
    }
}
