package com.example.demo.unit.dto;

import com.example.demo.adapter.dto.PoliticianDto;
import com.example.demo.baseClasses.BaseClassForPoliticianDTOTests;
import com.example.demo.adapter.dto.PresidentialPoliticianDto;
import com.example.demo.adapter.dto.SenatorialPoliticianDto;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.domain.enums.Rating;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PresidentialDTOTest extends BaseClassForPoliticianDTOTests {

    @Test
    public void testEqualsLogic() {
        PoliticianDto actual = new PresidentialPoliticianDto(presidentialBuilder.build(), Rating.LOW, LAW_SIGNED);

        assertEquals(actual,
                new PoliticiansDtoMapper().mapToDTO(presidentialBuilder
                        .setMostSignificantLawPassed(LAW_SIGNED)
                        .build()));
    }

    @Test
    public void shouldNotBeEqualDueToType() {
        PoliticianDto actual = new SenatorialPoliticianDto(politicianBuilder.build(), Rating.LOW, monthsOfService, LAW_MADE);

        assertNotEquals(actual,
                new PoliticiansDtoMapper().mapToDTO(senatorialBuilder.build()));
    }

}
