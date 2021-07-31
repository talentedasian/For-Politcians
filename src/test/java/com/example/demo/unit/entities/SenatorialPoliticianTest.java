package com.example.demo.unit.entities;

import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.model.entities.politicians.Politicians;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SenatorialPoliticianTest {

    final String POLITICIAN_NUMBER = "123polNumber";

    Politicians.PoliticiansBuilder politicianBuilder;

    SenatorialBuilder senatorialBuilder;

    @BeforeEach
    public void setup() {
        politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
                .setFirstName("Test")
                .setLastName("Name");

        senatorialBuilder = new SenatorialBuilder(politicianBuilder).setTotalMonthsOfService(12);
    }


    @Test
    public void shouldReturnTrueWithSamePoliticianNumber() {
        var actualPolitician = senatorialBuilder.build();

        var samePoliticianNumber = senatorialBuilder.setBuilder(politicianBuilder.setPoliticianNumber(POLITICIAN_NUMBER))
                .buildWithDifferentBuilder();

        assertTrue(actualPolitician.equals(samePoliticianNumber));
    }
    
    @Test
    public void shouldReturnFalseWithDifferentPoliticianNumber() {
        var actualPolitician = senatorialBuilder.build();

        var polWrongNumber = senatorialBuilder.setBuilder(politicianBuilder.setPoliticianNumber("differentNumber"))
                .buildWithDifferentBuilder();

        assertFalse(actualPolitician.equals(polWrongNumber));
    }

    @Test
    public void shouldReturnFalseWithDifferentPoliticianType() {
        var differentPoliticianType = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder)
                .build();

        assertFalse(senatorialBuilder.build().equals(differentPoliticianType));
    }

    @Test
    public void shouldFailDueToNegativeMonthsOfService() {
        var differentPoliticianType = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politicianBuilder)
                .setTotalMonthsOfService(-90);

        assertThrows(IllegalStateException.class,
                () -> differentPoliticianType.buildWithDifferentBuilder());
    }

}
