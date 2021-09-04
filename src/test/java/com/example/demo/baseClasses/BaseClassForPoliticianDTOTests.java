package com.example.demo.baseClasses;

import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.PoliticianNumber;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import org.junit.jupiter.api.BeforeEach;

public class BaseClassForPoliticianDTOTests {

    protected final PoliticianNumber POLITICIAN_NUMBER = NumberTestFactory.POL_NUMBER();

    protected final String LAW_SIGNED = "Gas Taxification";
    protected final String LAW_MADE = "Very Important Law";

    protected Politicians.PoliticiansBuilder politicianBuilder;

    protected PoliticianTypes.PresidentialPolitician.PresidentialBuilder presidentialBuilder;
    protected PoliticianTypes.SenatorialPolitician.SenatorialBuilder senatorialBuilder;

    protected double TOTAL_RATING = 1.0D;
    protected Rating lowRating = new Rating(TOTAL_RATING, 2.0D);
    protected Rating decentRating = new Rating(TOTAL_RATING, 7.9D);
    protected Rating highRating = new Rating(TOTAL_RATING, 9.22D);
    protected Rating[] ratings = {lowRating, decentRating, highRating};

    protected int monthsOfService = 12;

    @BeforeEach
    public void setup() {
        politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
                .setFirstName("Test")
                .setLastName("Name")
                .setFullName()
                .setRating(lowRating);

        presidentialBuilder = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder);

        senatorialBuilder = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politicianBuilder).setTotalMonthsOfService(monthsOfService);
    }

}
