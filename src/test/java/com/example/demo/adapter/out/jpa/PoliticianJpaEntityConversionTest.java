package com.example.demo.adapter.out.jpa;

import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;

public class PoliticianJpaEntityConversionTest {

    final String LAW_SIGNED = "Random Law";
    final String FIRST_NAME = "firstName";
    final String LAST_NAME = "lastName";

    PoliticiansBuilder politicianBuilder;

    @BeforeEach
    public void setup() {
        politicianBuilder = new PoliticiansBuilder(POL_NUMBER())
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setRating(new Rating(0D, 0D))
                .setPoliticiansRating(null);
    }

    @Test
    public void shouldConvertPoliticianEntityToJpaEntity() throws Exception{
        var presidential = new PresidentialBuilder(politicianBuilder).build();

        var jpaEntity = new PoliticiansJpaEntity(presidential.retrievePoliticianNumber(), presidential.firstName(),
                presidential.lastName(), presidential.fullName(), RatingJpaEntity.from(presidential.getRating()),
                presidential.totalCountsOfRatings(), List.of());

        var expectedJpaEntity = new PresidentialJpaEntity(jpaEntity, null);

        Assertions.assertThat(PoliticiansJpaEntity.from(presidential))
                .isEqualTo(expectedJpaEntity);
    }

}
