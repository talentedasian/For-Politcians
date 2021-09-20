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

import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static java.net.URI.create;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void shouldThrowBadRequestWithInappropriateRaterAccountNumber() throws Exception{
        polRepo.save(politician);
        PoliticiansRating savedRating = ratingRepo.save(politiciansRating);

        final String inappropriateAccountNumber = String.valueOf(savedRating.getId());
        mvc.perform(get(create("/api/ratings/ratings/" + inappropriateAccountNumber)))
                .andExpect(status().isBadRequest());
    }

}
