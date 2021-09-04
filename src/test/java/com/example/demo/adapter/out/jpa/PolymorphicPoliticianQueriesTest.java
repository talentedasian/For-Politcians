package com.example.demo.adapter.out.jpa;

import com.example.demo.adapter.out.repository.PoliticianJpaAdapterRepository;
import com.example.demo.adapter.out.repository.PoliticiansJpaRepository;
import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import testAnnotations.DatabaseTest;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class PolymorphicPoliticianQueriesTest extends BaseClassTestsThatUsesDatabase {

    final String FIRST_NAME = "firstName";
    final String LAST_NAME = "lastName";

    final String ID = "1";

    @Autowired
    PoliticiansJpaRepository jpaRepo;

    PoliticianJpaAdapterRepository repo;

    Politicians politician = new Politicians.PoliticiansBuilder("FLPP-LFPP-0000")
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setRating(new Rating(0D, 0D))
            .setPoliticiansRating(null)
            .build();

    @BeforeEach
    public void setup() {
        repo = new PoliticianJpaAdapterRepository(jpaRepo);
    }

    @Test
    public void polymorphicQueryShouldEqualToSenatorialPolitician() throws PoliticianNotPersistableException {
        var senatorial = new SenatorialBuilder(politician)
                .setTotalMonthsOfService(12)
                .build();

        repo.save(senatorial);

        assertThat(repo.findByPoliticianNumber(senatorial.retrievePoliticianNumber()).get())
                .isEqualTo(senatorial);
    }

    @Test
    public void polymorphicQueryShouldEqualToPresidentialPolitician() throws PoliticianNotPersistableException {
        var presidential = new PresidentialBuilder(politician)
                .build();

        repo.save(presidential);

        assertThat(repo.findByPoliticianNumber(presidential.retrievePoliticianNumber()).get())
                .isEqualTo(presidential);
    }

}
