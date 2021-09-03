package com.example.demo.baseClasses;

import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.politicians.Politicians;

public class BuilderFactory {

    public static UserRater createRater(String accountNumber) {
        return new UserRater.Builder()
                .setAccountNumber(accountNumber)
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();
    }

    public static PoliticiansRating createPolRating(double rating, UserRater rater, Politicians politicians) {
        return new PoliticiansRating.Builder()
                .setRating(rating)
                .setRater(rater)
                .setPolitician(politicians)
                .build();
    }

}
