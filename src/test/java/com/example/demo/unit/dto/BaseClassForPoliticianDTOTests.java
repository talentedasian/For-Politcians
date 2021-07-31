package com.example.demo.unit.dto;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.model.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class BaseClassForPoliticianDTOTests {

    protected final String POLITICIAN_NUMBER = "123polNumber";

    protected final String LAW_SIGNED = "Gas Taxification";

    protected Politicians.PoliticiansBuilder politicianBuilder;

    protected PoliticianTypes.PresidentialPolitician.PresidentialBuilder presidentialBuilder;
    protected PoliticianTypes.SenatorialPolitician.SenatorialBuilder senatorialBuilder;

    protected double TOTAL_RATING = 1.0D;
    protected com.example.demo.model.entities.Rating lowRating = new com.example.demo.model.entities.Rating(TOTAL_RATING, 2.0D, mock(LowSatisfactionAverageCalculator.class));
    protected com.example.demo.model.entities.Rating decentRating = new com.example.demo.model.entities.Rating(TOTAL_RATING, 7.9D, mock(DecentSatisfactionAverageCalculator.class));
    protected com.example.demo.model.entities.Rating highRating = new com.example.demo.model.entities.Rating(TOTAL_RATING, 9.22D, mock(HighSatisfactionAverageCalculator.class));
    protected com.example.demo.model.entities.Rating[] ratings = {lowRating, decentRating, highRating};

    protected String[] lawSignedSequentially = {"Rice Law", "Any Law", "Gas Oil"};

    protected final String LAW_MADE = "Very Important Law";
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

    protected List<PoliticianDTO> streamOfPresidentialPoliticianDTO() {
        List<PoliticianDTO> values = new ArrayList<>();

        int i = 0;
        while (i < 3) {
            values.add(new PoliticiansDtoMapper().mapToDTO(presidentialBuilder
                    .setMostSignificantLawPassed(lawSignedSequentially[i])
                    .setBuilder(politicianBuilder.setRating(ratings[i++]))
                    .buildWithDifferentBuilder()));
        }

        return values;
    }

    protected List<Politicians> streamOfPresidentialPoliticians() {
        List<Politicians> values = new ArrayList<>();

        int i = 0;
        while (i < 3) {
            values.add(presidentialBuilder
                    .setMostSignificantLawPassed(lawSignedSequentially[i])
                    .setBuilder(politicianBuilder.setRating(ratings[i++]))
                    .buildWithDifferentBuilder());
        }

        return values;
    }

}
