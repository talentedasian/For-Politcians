package com.example.demo.collaboration;

import com.example.demo.adapter.in.service.PoliticiansService;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.InMemoryPoliticianAdapterRepo;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.baseClasses.MultiplePoliticianSetup.pagedPoliticianSetupPresidential;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PoliticianApplicationServiceTest {

    final String FIRST_NAME = "Rodrigo";
    final String LAST_NAME = "Duterte";

    PoliticiansRepository polRepo;

    PoliticiansService polService;

    Politicians.PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(NumberTestFactory.POL_NUMBER())
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setPoliticiansRating(null)
            .setRating(new Rating(0D, 0D));

    @BeforeEach
    public void setup() {
        polRepo = new InMemoryPoliticianAdapterRepo();

        polService = new PoliticiansService(polRepo);
    }

    @Test
    public void shouldSavePoliticianInDatabase() throws PoliticianNotPersistableException {
        Politicians politician = new PresidentialBuilder(politicianBuilder)
                .build();

        var politicianSaved = polService.savePolitician(politician);

        assertThat(politicianSaved)
                .isEqualTo(polRepo.findByPoliticianNumber(politician.retrievePoliticianNumber()).get());
    }

    @Test
    public void shouldThrowPoliticianNotPersistableExceptionWhenTryingToSaveWithNullType() {
        var politician = politicianBuilder.build();

        assertThrows(PoliticianNotPersistableException.class, () -> polService.savePolitician(politician));
    }

    @Test
    public void shouldReturnCorrectPoliticianAndTypeWhenThereAreMultiplePoliticiansInDatabase() throws PoliticianNotPersistableException {
        Politicians presidential = new PresidentialBuilder(politicianBuilder)
                .build();
        Politicians senatorial = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politicianBuilder.setPoliticianNumber(NumberTestFactory.POL_NUMBER().politicianNumber() + "1"))
                .setTotalMonthsOfService(12)
                .build();

        polService.savePolitician(presidential);
        var politicianSaved = polService.savePolitician(senatorial);

        assertThat(politicianSaved)
                .isEqualTo(polRepo.findByPoliticianNumber(senatorial.retrievePoliticianNumber()).get());
    }

    @Test
    public void shouldReturnEmptyListWhenQueryingAPageThatDoesNotExist() throws Exception{
        pagedSetupForPoliticians(40);

        List<Politicians> emptyListOfPoliticians = polService.findAllWithPage(Page.of(41), 1).valuesAsList();

        assertThat(emptyListOfPoliticians)
                .isEmpty();
    }

    @Test
    public void shouldReturnCorrectPagedObjectWhenQueryingWithPage() throws Exception{
        int databaseSize = 40;
        pagedSetupForPoliticians(databaseSize);

        Page page = Page.of(3);
        int numberOfItemsToFetch = 10;
        PagedObject<Politicians> pagedPoliticians = polService.findAllWithPage(page, numberOfItemsToFetch);

        List<Politicians> expectedContent = pagedPoliticianSetupPresidential(databaseSize, politicianBuilder)
                .stream().skip(30).toList();

        assertThat(pagedPoliticians)
                .isEqualTo(PagedObject.of(expectedContent, databaseSize, numberOfItemsToFetch, page));
    }

    private void pagedSetupForPoliticians(int numberOfTimes) {
        pagedPoliticianSetupPresidential(numberOfTimes, politicianBuilder).forEach(it -> {
            try {
                polRepo.save(it);
            } catch (PoliticianNotPersistableException e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
    }

}
