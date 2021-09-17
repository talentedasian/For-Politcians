package com.example.demo.domain.entities;

import com.example.demo.domain.politicians.PoliticianNumber;

public interface UserRateLimitService {

    boolean isUserNotRateLimited(AccountNumber accountNumber, PoliticianNumber politicianNumber);

}
