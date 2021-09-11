package com.example.demo.adapter.out.jpa;

import com.example.demo.adapter.out.repository.PoliticianJpaAdapterRepository;
import com.example.demo.adapter.out.repository.PoliticiansJpaRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import testAnnotations.DatabaseTest;

import java.util.List;

import static com.example.demo.domain.politicians.PoliticianNumber.of;
import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class PoliticianJpaAdapterRepoTest extends BaseClassTestsThatUsesDatabase {

    @Autowired PoliticiansJpaRepository polJpaRepo;

    PoliticiansRepository polRepo;

    @BeforeEach
    public void setup() {
        polRepo = new PoliticianJpaAdapterRepository(polJpaRepo);
    }



    final String FIRST_NAME = "Rodrigo";
    final String LAST_NAME = "Duterte";

    Politicians.PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(NumberTestFactory.POL_NUMBER())
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setPoliticiansRating(null)
            .setRating(new Rating(0D, 0D));

    @Test
    public void testCustomNativeQueryForFindingPoliticiansWithOffsetAndLimitForPostgresql() throws Exception{
        pagedPoliticianSetup();

        List<PoliticiansJpaEntity> actualPagedPoliticians = polJpaRepo.findAllWithPage(20, 10);

        List<PoliticiansJpaEntity> EXPECTED_RESULT_FOR_NATIVE_QUERY = polJpaRepo.findAll().stream().skip(20).limit(10).toList();

        assertThat(actualPagedPoliticians)
                .hasSameElementsAs(EXPECTED_RESULT_FOR_NATIVE_QUERY);
    }

    @Test
    public void shouldReturnEmptyListWheneverCountIsLessThanItemsToSkipSpecifiedInFindAllWithPageQuery() throws Exception{
        var presidential = new PresidentialBuilder(politicianBuilder).build();

        polRepo.save(presidential);

        List<PoliticiansJpaEntity> shouldBeEmpty = polJpaRepo.findAllWithPage(3, 1);

        assertThat(shouldBeEmpty)
                .isEmpty();
    }

    @Test
    public void shouldSkipPoliticiansByPageNumberMultipliedBy10ThenFetchesItemsSpecifiedInQuery() throws Exception{
        pagedPoliticianSetup();

        PagedObject<Politicians> pagedPoliticians = polRepo.findAllByPage(Page.of(1), 20);

        List<Politicians> EXPECTED_2ND_PAGE_OF_PAGEDPOLITICIANS = polRepo.findAll().stream()
                .skip(Page.of(1).itemsToSkip(20)).limit(10).toList();

        assertThat(pagedPoliticians)
                .isEqualTo(PagedObject.of(EXPECTED_2ND_PAGE_OF_PAGEDPOLITICIANS, polJpaRepo.count(), 20, Page.of(1)));
    }

    private void pagedPoliticianSetup() throws PoliticianNotPersistableException {
        for (int i = 0; i < 30; i++) {
            Politicians presidential = new PresidentialBuilder(politicianBuilder
                    .setPoliticianNumber(of(NumberTestFactory.POL_NUMBER().politicianNumber().concat(valueOf(i))).politicianNumber()))
                    .build();

            polRepo.save(presidential);
        }
    }

    @AfterEach
    public void pagedPoliticianTeardown() throws PoliticianNotPersistableException {
        for (int i = 0; i < 30; i++) {
            Politicians presidential = new PresidentialBuilder(politicianBuilder
                    .setPoliticianNumber(of(NumberTestFactory.POL_NUMBER().politicianNumber().concat(valueOf(i))).politicianNumber()))
                    .build();

            try {
                polRepo.deleteByPoliticianNumber(presidential.retrievePoliticianNumber());
            } catch(EmptyResultDataAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
