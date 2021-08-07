package com.example.demo.unit.dto;

import com.example.demo.baseClasses.BaseClassForPoliticianDTOTests;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dto.SenatorialPoliticianDTO;
import com.example.demo.dtoRequest.AddSenatorialPoliticianDTORequest;
import com.example.demo.dtomapper.PoliticianDTOUnwrapper;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.enums.Rating;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SenatorialDTOTest extends BaseClassForPoliticianDTOTests {

    @Test
    public void testEqualsLogic() {
        PoliticianDTO actual = new SenatorialPoliticianDTO(politicianBuilder.build(), Rating.LOW, monthsOfService, LAW_MADE);

        assertEquals(actual,
                new PoliticiansDtoMapper().mapToDTO(senatorialBuilder
                        .setMostSignificantLawMade(LAW_MADE)
                        .build()));
    }

    @Test
    public void shouldNotBeEqualDueToType() {
        PoliticianDTO actual = new SenatorialPoliticianDTO(politicianBuilder.build(), Rating.LOW, monthsOfService, LAW_MADE);

        assertNotEquals(actual,
                new PoliticiansDtoMapper().mapToDTO(presidentialBuilder
                        .setMostSignificantLawPassed(LAW_SIGNED)
                        .build()));
    }

    @Test
    public void shouldEqualHashCodeWithEqualPoliticianNumberAndSameType() {
        Politicians actual = new PoliticianDTOUnwrapper()
                .unWrapDTO(new AddSenatorialPoliticianDTORequest("Test", "Name", BigDecimal.ZERO, 12, null));
        actual.setPoliticianNumber(POLITICIAN_NUMBER);

        assertEquals(actual.hashCode(),
                new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
                        .setId(1)
                        .setFirstName("Test")
                        .setLastName("Name")
                        .setPoliticiansRating(new ArrayList<PoliticiansRating>())
                        .setRating(new com.example.demo.model.entities.Rating(0.01D, 0.01D, Mockito.mock(LowSatisfactionAverageCalculator.class))))
                        .setTotalMonthsOfService(12)
                        .build()
                        .hashCode());
    }

}
