package com.example.demo.domain;

import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
    Citizens refer to basically the users who rate politicians
 */
public class CitizensRatingPoliticiansTest {

    Politicians politicians;

    @BeforeEach
    public void setup() {
        politicians = new PoliticiansBuilder("dummy")
                .setPoliticiansRating(null)
                .setFirstName("Random")
                .setLastName("Name")
                .setFullName()
                .setRating(new Rating(0D, 0D))
                .build();
    }

    @Test
    public void ratingShouldBeCalculatedAsExpectedWhenRatePoliticianCalled() {
        
    }

}
