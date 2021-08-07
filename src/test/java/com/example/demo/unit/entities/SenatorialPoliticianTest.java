package com.example.demo.unit.entities;

import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.model.entities.politicians.Politicians;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

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
                .build();

        assertTrue(actualPolitician.equals(samePoliticianNumber));
    }
    
    @Test
    public void shouldReturnFalseWithDifferentPoliticianNumber() {
        var actualPolitician = senatorialBuilder.build();

        var polWrongNumber = senatorialBuilder.setBuilder(politicianBuilder.setPoliticianNumber("differentNumber"))
                .build();

        assertFalse(actualPolitician.equals(polWrongNumber));
    }

    @Test
    public void shouldReturnFalseWithDifferentPoliticianType() {
        var differentPoliticianType = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder)
                .build();

        assertFalse(senatorialBuilder.build().equals(differentPoliticianType));
    }

    @Test
    public void shouldReturnObjectInHashMap() {
        var actualPolitician = senatorialBuilder.build();

        Map<Politicians,Politicians> map = new HashMap<>();
        map.put(actualPolitician,actualPolitician);

        var samePoliticianNumber = senatorialBuilder.setBuilder(politicianBuilder.setPoliticianNumber(POLITICIAN_NUMBER))
                .build();

        assertEquals(actualPolitician, map.get(samePoliticianNumber));
    }

    /*
        do not put -0 as value source. it's automatically converted to a positive 0 and test will fail
        due to external factors.
     */
    @ParameterizedTest
    @ValueSource(ints = { -90, -132, -33, -312, -1 })
    public void shouldFailDueToNegativeMonthsOfService(int monthsOfService) {
        var differentPoliticianType = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politicianBuilder)
                .setTotalMonthsOfService(monthsOfService);

        assertThrows(IllegalStateException.class,
                () -> differentPoliticianType.build());
    }

    @Test
    public void keyShouldNotBeInMapEvenWithSamePoliticianNumber() {
        var actualPolitician = senatorialBuilder.build();

        Map<Politicians,Politicians> map = new HashMap<>();
        map.put(actualPolitician,actualPolitician);

        var differentPoliticianType = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder)
                .build();

        assertFalse(map.containsKey(differentPoliticianType));
    }

}
