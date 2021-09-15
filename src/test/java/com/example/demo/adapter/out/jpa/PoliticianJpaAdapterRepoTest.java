package com.example.demo.adapter.out.jpa;

import com.example.demo.adapter.out.repository.PoliticianJpaAdapterRepository;
import com.example.demo.adapter.out.repository.PoliticiansJpaRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.InMemoryPoliticianAdapterRepo;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.Name;
import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import testAnnotations.DatabaseTest;

import java.util.List;

import static com.example.demo.baseClasses.MultiplePoliticianSetup.pagedPoliticianSetupPresidential;
import static com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory.politicianCalculator;
import static com.example.demo.domain.politicians.PoliticianNumber.of;
import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class PoliticianJpaAdapterRepoTest extends BaseClassTestsThatUsesDatabase {

    @Autowired PoliticiansJpaRepository polJpaRepo;

    PoliticiansRepository polRepo;

    InMemoryPoliticianAdapterRepo inMemoryPolRepo;

    @BeforeEach
    public void setup() {
        polRepo = new PoliticianJpaAdapterRepository(polJpaRepo);

        inMemoryPolRepo = new InMemoryPoliticianAdapterRepo();
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

        PagedObject<Politicians> pagedPoliticians = polRepo.findAllByPage(Page.of(1), 20, null);

        List<Politicians> EXPECTED_2ND_PAGE_OF_PAGEDPOLITICIANS = polRepo.findAll().stream()
                .skip(Page.of(1).itemsToSkip(20)).limit(10).toList();

        assertThat(pagedPoliticians)
                .isEqualTo(PagedObject.of(EXPECTED_2ND_PAGE_OF_PAGEDPOLITICIANS, polJpaRepo.count(), 20, Page.of(1)));
    }

    @Test
    public void testOrderByForIdInPostgresql() throws Exception{
        pagedPoliticianSetup();

        List<Politicians> pagedPoliticians = polJpaRepo.findAllWithPage(20, 10)
                .stream().map(PoliticiansJpaEntity::toPoliticians).toList();

        List<Politicians> EXPECTED_2ND_PAGE_OF_PAGEDPOLITICIANS = pagedPoliticianSetupPresidential(30, politicianBuilder).stream()
                .skip(Page.of(1).itemsToSkip(20)).limit(10).toList();

        assertThat(pagedPoliticians)
                .isEqualTo(EXPECTED_2ND_PAGE_OF_PAGEDPOLITICIANS);
    }

    @Test
    public void testIfSortOfInMemoryRepoIsParallelWithTheJpaRepoWithSameLettersInId() throws Exception{
        pagedPoliticianSetup();

        inMemorySetup();

        List<Politicians> postgresSortImplementation = polJpaRepo.findAllWithPage(0, 30)
                .stream().map(PoliticiansJpaEntity::toPoliticians).toList();

        List<Politicians> inMemorySortImplementation = inMemoryPolRepo.findAll().stream().toList();

        assertThat(postgresSortImplementation)
                .isEqualTo(inMemorySortImplementation);
    }

    @Test
    public void testIfSortOfInMemoryRepoIsParallelWithTheJpaRepoWithDifferentLettersAndDigits() throws Exception{
        inMemorySetupWithNoOrder();
        List<Politicians> postgresSortImplementation = polJpaRepo.findAllWithPage(0, 3)
                .stream().map(PoliticiansJpaEntity::toPoliticians).toList();

        List<Politicians> inMemorySortImplementation = inMemoryPolRepo.findAll().stream().toList();

        assertThat(postgresSortImplementation)
                .isEqualTo(inMemorySortImplementation);
    }

    private void inMemorySetupWithNoOrder() throws PoliticianNotPersistableException {
        Name[] names = {Name.of("Name", "Any"), Name.of("Good", "One"), Name.of("Real", "Fake")};
        Politicians.Type[] types = {Politicians.Type.SENATORIAL, Politicians.Type.PRESIDENTIAL, Politicians.Type.SENATORIAL};

        String POL_NUMBER;

        for (int i = 0; i < names.length; i++) {
            var polNumberCalculator = politicianCalculator(types[i]);
            POL_NUMBER = polNumberCalculator.calculatePoliticianNumber(names[i]).politicianNumber();
            var builder = politicianBuilder.setPoliticianNumber(POL_NUMBER);


            if (types[i].equals(Politicians.Type.PRESIDENTIAL)) {
                var presidential = new PresidentialBuilder(builder).build();
                polRepo.save(presidential);
                inMemoryPolRepo.save(presidential);
            } else {
                var senatorial = new SenatorialBuilder(builder).setTotalMonthsOfService(12).build();
                polRepo.save(senatorial);
                inMemoryPolRepo.save(senatorial);
            }

        }

    }

    private void inMemorySetup() {
        pagedPoliticianSetupPresidential(30 ,politicianBuilder)
                .forEach(it -> {
                    try {
                        inMemoryPolRepo.save(it);
                    } catch (PoliticianNotPersistableException e) {
                        e.printStackTrace();
                    }
                });
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
            polRepo.findAll().stream()
                    .map(it -> it.retrievePoliticianNumber())
                    .forEach(it -> {
                        try {
                            polRepo.deleteByPoliticianNumber(it);
                            inMemoryPolRepo.deleteByPoliticianNumber(it);
                        } catch(EmptyResultDataAccessException e) {
                            e.printStackTrace();
                        }
                    });
        }

}
