package com.example.demo.adapter.web;

import com.example.demo.BaseSpringHateoasTest;
import com.example.demo.adapter.out.repository.PoliticiansJpaRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.entities.Name;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.entities.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.entities.Politicians.PoliticiansBuilder;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

import static com.example.demo.baseClasses.MultiplePoliticianSetup.pagedPoliticianSetupPresidential;
import static com.example.demo.baseClasses.MultiplePoliticianSetup.pagedPoliticianSetupSenatorial;
import static com.example.demo.domain.entities.Politicians.Type.PRESIDENTIAL;
import static com.example.demo.domain.enums.Rating.HIGH;
import static com.example.demo.domain.enums.Rating.LOW;
import static com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory.politicianCalculator;
import static java.math.BigDecimal.valueOf;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
public class PoliticianHttpAdapterTest extends BaseSpringHateoasTest {

    final String presidentialBasePath = "_embedded.presidentialPoliticianDtoList";
    final String senatorialBasePath = "_embedded.senatorialPoliticianDtoList";

    final String FIRST_NAME = "Mirriam";
    final String LAST_NAME = "Defensor";

    @Autowired PoliticiansRepository polRepo;

    @Autowired PoliticiansJpaRepository jpaRepo;

    PoliticiansBuilder politicianBuilder;

    final PoliticianNumber POLITICIAN_NUMBER = politicianCalculator(PRESIDENTIAL).calculatePoliticianNumber(Name.of(FIRST_NAME, LAST_NAME));

    String requestContent = """
            {
                "first_name" : "Mirriam",
                "last_name" : "Defensor",
                "rating" : 0.09,
                "most_significant_law_signed" : "Random Law",
                "type" : "PRESIDENTIAL"
            }
            """;

    @BeforeEach
    public void setup() {
        politicianBuilder = new PoliticiansBuilder(POLITICIAN_NUMBER)
                .setPoliticiansRating(null)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME);
    }

    @Test
    public void shouldReturnLinks_SelfAndRatePolitician() throws Exception{
        var presidential = new PresidentialBuilder(politicianBuilder).build();

        polRepo.save(presidential);

        mvc.perform(get("/api/politicians/politician"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_FORMS_JSON))

                    .andDo(document("politician", links(halLinks(),
                            linkWithRel("self").description("Link that points to politician resource"),
                            linkWithRel("rate-politician").description("Link that rates politicians"))));
        }

    @Test
    public void shouldSaveToDatabaseGivenWithCorrectAuthorization() throws Exception{
        var politician = new PresidentialBuilder(politicianBuilder)
                .setMostSignificantLawPassed("Random Law").build();

        mvc.perform(post(create("/api/politicians/politician"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Politician-Access", "password")
                        .content(requestContent))
                .andExpect(status().isCreated())

                    .andExpect(jsonPath("id", notNullValue()))
                    .andExpect(jsonPath("rating", equalTo("N/A")));
    }

    @Test
    public void shouldReturn200OKAndCorrectFieldsWhenFindingIndividualPoliticians() throws Exception{
        var politician = new PresidentialBuilder(politicianBuilder)
                .setMostSignificantLawPassed("Random Law").build();

        polRepo.save(politician);

        mvc.perform(get(create("/api/politicians/politician/" + POLITICIAN_NUMBER.politicianNumber()))
                    .contentType(MediaTypes.HAL_FORMS_JSON))
            .andExpect(status().isOk())

                .andExpect(jsonPath("id", equalTo(politician.retrievePoliticianNumber())))
                .andExpect(jsonPath("rating", equalTo("N/A")))
                .andExpect(jsonPath("type", equalTo("PRESIDENTIAL")));
    }

    @Test
    public void listOfPoliticiansShouldSuccessfullyReturnPolymorphicPoliticiansAndShouldHaveSelfLinkIncluded() throws Exception{
        var presidential = new PresidentialBuilder(politicianBuilder)
                .setMostSignificantLawPassed("Random Law").build();
        var senatorial = new SenatorialBuilder(politicianBuilder
                            .setPoliticianNumber(POLITICIAN_NUMBER.politicianNumber() + "1")
                            .setAverageRating(AverageRating.of(valueOf(9)))
                            .setTotalRating(valueOf(2)))
                .setTotalMonthsOfService(44).build();

        polRepo.save(presidential);
        polRepo.save(senatorial);

        mvc.perform(get("/api/politicians")
                        .contentType(MediaTypes.HAL_FORMS_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_FORMS_JSON))

                    .andExpect(jsonPath(presidentialBasePath.concat("[0].id"), equalTo(presidential.retrievePoliticianNumber())))
                    .andExpect(jsonPath(presidentialBasePath.concat("[0].name"), equalTo(presidential.fullName())))
                    .andExpect(jsonPath(presidentialBasePath.concat("[0].rating"), equalTo("N/A")))
                    .andExpect(jsonPath(presidentialBasePath.concat("[0].satisfaction_rate"), equalTo(LOW.toString())))
                    .andExpect(jsonPath(presidentialBasePath.concat("[0].most_significant_law_signed"), equalTo(presidential.getMostSignificantLawSigned())))
                    .andExpect(jsonPath(senatorialBasePath.concat("[0].id"), equalTo(senatorial.retrievePoliticianNumber())))
                    .andExpect(jsonPath(senatorialBasePath.concat("[0].name"), equalTo(senatorial.fullName())))
                    .andExpect(jsonPath(senatorialBasePath.concat("[0].rating"), equalTo(String.valueOf(senatorial.averageRating()))))
                    .andExpect(jsonPath(senatorialBasePath.concat("[0].satisfaction_rate"), equalTo(HIGH.toString())))

                .andDo(document("politician", links(halLinks(),
                        linkWithRel("self").description("Link that points to all politicians"))));
    }

    @Test
    public void shouldReturnExpectedItemSizeWhenItemSizeIsLessThanTheCountOfAllInTheDatabase() throws Exception{
       pagedPoliticianSetupPresidential(30, politicianBuilder).stream().forEach(it -> {
           try {
               polRepo.save(it);
           } catch (PoliticianNotPersistableException e) {
               e.printStackTrace();
           }
       });

       mvc.perform(get(URI.create("/api/politicians/politicians?page=0&items=20")))
               .andExpect(status().isOk())

                    .andExpect(jsonPath(presidentialBasePath, hasSize(20)))

               .andDo(document("politician", links(halLinks(),
                       linkWithRel("self").description("Link that points to all politicians with pagination"))));
    }

    @Test
    public void shouldReturnExpectedItemSizeForPolymorphicPaginatedQuery() throws Exception{
        jpaRepo.deleteAll();
        pagedPoliticianSetupPresidential(10, politicianBuilder).stream().forEach(it -> {
            try {
                polRepo.save(it);
            } catch (PoliticianNotPersistableException e) {
                e.printStackTrace();
            }
        });
        pagedPoliticianSetupSenatorial(19, politicianBuilder).stream().forEach(it -> {
            try {
                polRepo.save(it);
            } catch (PoliticianNotPersistableException e) {
                e.printStackTrace();
            }
        });

        mvc.perform(get(URI.create("/api/politicians/politicians?page=0&items=40")))
                .andExpect(status().isOk())

                .andExpect(jsonPath(presidentialBasePath, hasSize(10)))
                .andExpect(jsonPath(senatorialBasePath, hasSize(19)))

                .andDo(document("politician", links(halLinks(),
                        linkWithRel("self").description("Link that points to all politicians with pagination"))));
    }

}
