package com.example.demo.unit.dto;

import com.example.demo.baseClasses.BaseClassForPoliticianDTOTests;
import com.example.demo.domain.entities.Rating;
import com.example.demo.adapter.dto.PoliticianDTO;
import com.example.demo.adapter.dto.SenatorialPoliticianDTO;
import com.example.demo.adapter.in.dtoRequest.AddSenatorialPoliticianDTORequest;
import com.example.demo.dtomapper.PoliticianDTOUnwrapper;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SenatorialDTOTest extends BaseClassForPoliticianDTOTests {

    @Test
    public void testEqualsLogic() {
        PoliticianDTO actual = new SenatorialPoliticianDTO(politicianBuilder.build(), com.example.demo.domain.enums.Rating.LOW, monthsOfService, LAW_MADE);

        assertEquals(actual,
                new PoliticiansDtoMapper().mapToDTO(senatorialBuilder
                        .setMostSignificantLawMade(LAW_MADE)
                        .build()));
    }

    @Test
    public void shouldNotBeEqualDueToType() {
        PoliticianDTO actual = new SenatorialPoliticianDTO(politicianBuilder.build(), com.example.demo.domain.enums.Rating.LOW, monthsOfService, LAW_MADE);

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
                            .setRating(new Rating(0.01D, 0.01D)))
                        .setTotalMonthsOfService(12)
                        .build()
                        .hashCode());
    }

}
