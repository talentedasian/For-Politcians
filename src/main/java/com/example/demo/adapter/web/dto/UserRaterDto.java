package com.example.demo.adapter.web.dto;

import com.example.demo.domain.entities.UserRater;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRaterDto {

    private final String accountNumber, name, email;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
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
                ", fullName='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static UserRaterDto from(UserRater rater) {
        return new UserRaterDto(rater.returnUserAccountNumber(), rater.name(), rater.email());
    }

    public UserRater toUserRater() {
        return new UserRater.Builder()
                .setName(name)
                .setEmail(email)
                .setAccountNumber(accountNumber)
                .build();
    }
}
