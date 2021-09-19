package com.example.demo.collaboration;

import com.example.demo.adapter.in.service.RatingService;
import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.baseClasses.BuilderFactory;
import com.example.demo.domain.DefaultRateLimitDomainService;
import com.example.demo.domain.InMemoryPoliticianAdapterRepo;
import com.example.demo.domain.InMemoryRatingAdapterRepo;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.Politicians.PoliticiansBuilder;
import com.example.demo.domain.entities.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.demo.baseClasses.BuilderFactory.createRater;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;

public class RatingApplicationServiceTest {

    RatingRepository ratingRepo;
    PoliticiansRepository polRepo;
    RateLimitRepository rateLimitRepo;

    @BeforeEach
    public void setup() {
        ratingRepo = new InMemoryRatingAdapterRepo();

        polRepo = new InMemoryPoliticianAdapterRepo();

        rateLimitRepo = new InMemoryRateLimitRepository();
    }

    @Test
    public void ratingPoliticianShouldSaveToDatabase() throws Exception{
        //GIVEN
        var fakeRater = createRater(ACC_NUMBER().accountNumber());

        var justToIncreaseSize = BuilderFactory.createPolRating(2.2132131D, fakeRater, null);

        PresidentialPolitician politician = new PresidentialBuilder(new PoliticiansBuilder(POL_NUMBER())
                        .setPoliticiansRating(List.of(justToIncreaseSize, justToIncreaseSize, justToIncreaseSize, justToIncreaseSize))
                        .setRating(new Rating(4D, 4.989D)))
                .build();

        var raterWhoRates = createRater(ACC_NUMBER().accountNumber().concat("1"));

        var actualRatingForPolitician = BuilderFactory.createPolRating(5.99323D, raterWhoRates, politician);

        var service = new RatingService(ratingRepo, polRepo, new DefaultRateLimitDomainService(rateLimitRepo));

        //WHEN
        polRepo.save(politician);
        service.saveRatings(actualRatingForPolitician);

        Optional<Politicians> politicianQueried = polRepo.findByPoliticianNumber(POL_NUMBER().politicianNumber());

        //THEN
        assertThat(politicianQueried)
                .isPresent();
        assertThat(politicianQueried.get())
                .isEqualTo(politician);
    }

}
