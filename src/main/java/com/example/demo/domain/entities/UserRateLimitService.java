package com.example.demo.domain.entities;

public interface UserRateLimitService {

    boolean isUserNotRateLimited(AccountNumber accountNumber);

}
