package com.example.demo.adapter.out.jpa;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.enums.PoliticalParty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class UserRaterJpaEntity {

    @Autowired transient RateLimitRepository rateLimitRepository;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "party")
    private PoliticalParty politicalParties;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "account_number")
    private String userAccountNumber;

    UserRaterJpaEntity(String name, PoliticalParty politicalParties, String email, String userAccountNumber) {
        this.name = name;
        this.politicalParties = politicalParties;
        this.email = email;
        this.userAccountNumber = userAccountNumber;
    }

    public static UserRaterJpaEntity from(UserRater rater) {
        return new Builder()
                .setUserAccountNumber(rater.getUserAccountNumber())
                .setPoliticalParties(rater.getPoliticalParties())
                .setFacebookName(rater.getFacebookName())
                .setEmail(rater.getEmail())
                .build();
    }

    public UserRater toUserRater() {
        return new UserRater.Builder()
                .setAccountNumber(userAccountNumber)
                .setName(name)
                .setPoliticalParty(politicalParties)
                .setRateLimitRepo(rateLimitRepository)
                .setEmail(email)
                .build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PoliticalParty getPoliticalParties() {
        return politicalParties;
    }

    public void setPoliticalParties(PoliticalParty politicalParties) {
        this.politicalParties = politicalParties;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserAccountNumber() {
        return userAccountNumber;
    }

    public void setUserAccountNumber(String userAccountNumber) {
        this.userAccountNumber = userAccountNumber;
    }

    public static class Builder {

        private String facebookName;

        private PoliticalParty politicalParties;

        private String email;

        private String userAccountNumber;

        public Builder setFacebookName(String facebookName) {
            this.facebookName = facebookName;
            return this;
        }

        public Builder setPoliticalParties(PoliticalParty politicalParties) {
            this.politicalParties = politicalParties;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setUserAccountNumber(String userAccountNumber) {
            this.userAccountNumber = userAccountNumber;
            return this;
        }

        public UserRaterJpaEntity build() {
            return new UserRaterJpaEntity(facebookName, politicalParties, email, userAccountNumber);
        }
    }

}
