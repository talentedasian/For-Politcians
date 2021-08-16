package com.example.demo.adapter.dto;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.UserRater;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRaterDto {

    private final String accountNumber, name, email;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getname() {
        return name;
    }

    public UserRaterDto(String accountNumber, String name, String email) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserRaterDto{" +
                "accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static UserRaterDto from(UserRater rater) {
        return new UserRaterDto(rater.getUserAccountNumber(), rater.getFacebookName(), rater.getEmail());
    }

    public UserRater toUserRater(RateLimitRepository rateLimitRepo) {
        return new UserRater.Builder()
                .setName(name)
                .setEmail(email)
                .setAccountNumber(accountNumber)
                .setRateLimitRepo(rateLimitRepo)
                .build();
    }
}
