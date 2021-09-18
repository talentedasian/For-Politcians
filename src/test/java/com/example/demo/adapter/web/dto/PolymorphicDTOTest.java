package com.example.demo.adapter.web.dto;

import com.example.demo.baseClasses.BaseClassForPoliticianDTOTests;
import com.example.demo.domain.entities.PoliticianTypes;
import com.example.demo.domain.entities.Politicians;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PolymorphicDTOTest extends BaseClassForPoliticianDTOTests {

    @Test
    public void testPolymorphicDTOMapper() {
        var presidentialPolitician = new PoliticianTypes.PresidentialPolitician
                .PresidentialBuilder(politicianBuilder
                    .setFirstName("Ninoy")
                    .setLastName("Aquino")
                    .setFullName())
                .build();

        var senatorialPolitician = new PoliticianTypes.SenatorialPolitician
                .SenatorialBuilder(politicianBuilder
                    .setFirstName("Nancy")
                    .setLastName("Binay")
                    .setFullName())
                .setTotalMonthsOfService(monthsOfService)
                .build();

        List<Politicians> listOfPolymorphicPoliticians = List.of(presidentialPolitician,senatorialPolitician);

        assertThat(streamOfPoliticians())
                .containsAll(listOfPolymorphicPoliticians);
    }

    private List<Politicians> streamOfPoliticians() {
        return List.of(
                senatorialBuilder
                        .setBuilder(politicianBuilder
                                .setFirstName("Nancy")
                                .setLastName("Binay")
                                .setFullName())
                        .build(),
                presidentialBuilder
                        .setBuilder(politicianBuilder
                                .setFirstName("Ninoy")
                                .setLastName("Aquino")
                                .setFullName())
                        .build());
    }

}
