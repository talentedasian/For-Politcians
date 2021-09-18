package com.example.demo.adapter.out.jpa;

import com.example.demo.adapter.out.repository.PoliticianJpaAdapterRepository;
import com.example.demo.adapter.out.repository.PoliticiansJpaRepository;
import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.entities.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import testAnnotations.DatabaseTest;

import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class PolymorphicPoliticianPersistenceTest extends BaseClassTestsThatUsesDatabase {

    final String FIRST_NAME = "firstName";
    final String LAST_NAME = "lastName";

    final String ID = "1";

    @Autowired PoliticiansJpaRepository jpaRepo;

    PoliticianJpaAdapterRepository repo;

    Politicians politician = new Politicians.PoliticiansBuilder(POL_NUMBER())
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
    public void shouldSavePoliticianOntoDatabaseWithCorrectTypeByPresidential() throws PoliticianNotPersistableException {
        var presidential = new PresidentialBuilder(politician)
                .setMostSignificantLawPassed("any law")
                .build();

        repo.save(presidential);
        var politicianJpaEntity = PoliticiansJpaEntity.from(presidential);

        assertThat(jpaRepo.findById(presidential.retrievePoliticianNumber()).get())
                .isEqualTo(politicianJpaEntity);
    }

    @Test
    public void shouldSavePoliticianOntoDatabaseWithCorrectTypeBySenatorial() throws PoliticianNotPersistableException {
        var senatorial = new SenatorialBuilder(politician)
                .setTotalMonthsOfService(12)
                .build();

        repo.save(senatorial);
        var politicianJpaEntity = PoliticiansJpaEntity.from(senatorial);

        assertThat(jpaRepo.findById(senatorial.retrievePoliticianNumber()).get())
                .isEqualTo(politicianJpaEntity);
    }

}
