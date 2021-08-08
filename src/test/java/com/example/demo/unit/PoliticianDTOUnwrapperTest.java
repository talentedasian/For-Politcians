package com.example.demo.unit;

import com.example.demo.dtoRequest.AddSenatorialPoliticianDTORequest;
import com.example.demo.dtomapper.PoliticianDTOUnwrapper;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.model.entities.politicians.Politicians.PoliticiansBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PoliticianDTOUnwrapperTest {

    double TOTAL_RATING = 0.00D;

    final String LAST_NAME = "Binay-Angeles";
    final String FIRST_NAME = "Maria Lourdes Nancy Sombillo";

    PoliticiansBuilder politicianBuilder = new PoliticiansBuilder("dummy")
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setFullName()
            .setRating(new Rating(TOTAL_RATING, 0.00D));

    SenatorialBuilder senatorialBuilder = new SenatorialBuilder(politicianBuilder)
            .setTotalMonthsOfService(12);

    @Test
    public void testUnwrappingOfDTOToPolitician() {
        var dtoRequest = new AddSenatorialPoliticianDTORequest(FIRST_NAME, LAST_NAME, BigDecimal.ZERO, 12, null);

        assertEquals(senatorialBuilder.build(), new PoliticianDTOUnwrapper().unWrapDTO(dtoRequest));
    }


}
