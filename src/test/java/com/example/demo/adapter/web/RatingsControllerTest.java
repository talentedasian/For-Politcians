package com.example.demo.adapter.web;

import com.example.demo.BaseSpringHateoasTest;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.enums.PoliticalParty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;

import static com.example.demo.baseClasses.MockMvcAssertions.assertThat;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RatingsControllerTest extends BaseSpringHateoasTest {

    @Autowired RatingRepository ratingRepo;
    @Autowired PoliticiansRepository polRepo;

    PresidentialPolitician politician = new PresidentialBuilder(new Politicians.PoliticiansBuilder(POL_NUMBER())
            .setFirstName("Fake")
            .setRating(new Rating(4D, 4.989D)))
            .build();

    UserRater rater = new UserRater.Builder()
            .setName("Fake")
            .setAccountNumber(ACC_NUMBER().accountNumber())
            .setEmail("test@gmail.com")
            .setPoliticalParty(PoliticalParty.DDS)
            .build();
    PoliticiansRating politiciansRating = new PoliticiansRating.Builder(politician)
            .setRater(rater)
            .setRating(1D)
            .build();

    @Test
    @Rollback
    public void shouldThrowBadRequestWithInappropriateRaterAccountNumber() throws Exception{
        polRepo.save(politician);
        PoliticiansRating savedRating = ratingRepo.save(politiciansRating);

        final String inappropriateAccountNumber = String.valueOf(savedRating.getId());
        mvc.perform(get(create("/api/ratings/ratings/" + inappropriateAccountNumber)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Rollback
    public void shouldReturnInappropriateAccountNumberAsBody() throws Exception{
        polRepo.save(politician);
        PoliticiansRating savedRating = ratingRepo.save(politiciansRating);

        final String inappropriateAccountNumber = String.valueOf(savedRating.getId());
        MvcResult response = mvc.perform(get(create("/api/ratings/ratings/" + inappropriateAccountNumber))).andReturn();

        assertThat(response)
                .hasPath("reason")
                .isEqualTo("Inappropriate account number given")
                .hasPath("action")
                .isEqualTo("Check appropriate account numbers for valid account numbers");
    }

    @Test
    public void shouldReturnRatingWithPolitician() throws Exception{
        polRepo.save(politician);
        PoliticiansRating savedRating = ratingRepo.save(politiciansRating);

        mvc.perform(get(create("/api/ratings/rating/" + savedRating.getId())))
                .andExpect(status().isOk())

                .andExpect(jsonPath("rating", equalTo(savedRating.getRating())))
                .andExpect(jsonPath("id", equalTo(savedRating.getId().toString())))
                .andExpect(jsonPath("politician.id", equalTo(politician.retrievePoliticianNumber())));
    }

    @Test
    public void shouldHaveSelfLink() throws Exception{
        polRepo.save(politician);
        PoliticiansRating savedRating = ratingRepo.save(politiciansRating);

        mvc.perform(get(create("/api/ratings/rating/" + savedRating.getId())))
                .andExpect(content().contentType(MediaTypes.HAL_FORMS_JSON))

                    .andDo(document("rating", links(halLinks(),
                            linkWithRel("self").description("Link that points to the rating entity"))));
    }

}
