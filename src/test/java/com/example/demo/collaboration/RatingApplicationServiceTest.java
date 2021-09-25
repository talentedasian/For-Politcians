package com.example.demo.collaboration;

import com.example.demo.adapter.in.service.RatingService;
import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.baseClasses.BuilderFactory;
import com.example.demo.domain.*;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.Politicians.PoliticiansBuilder;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.demo.baseClasses.BuilderFactory.createRater;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static java.math.BigDecimal.valueOf;
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

        var justToIncreaseSize = BuilderFactory.createPolRating(Score.of(2.2132131), fakeRater, null);

        PresidentialPolitician politician = new PresidentialBuilder(new PoliticiansBuilder(POL_NUMBER())
                        .setPoliticiansRating(List.of(justToIncreaseSize, justToIncreaseSize, justToIncreaseSize, justToIncreaseSize))
                        .setRating(new Rating(4D, AverageRating.of(valueOf(4.989D)))))
                .build();

        var raterWhoRates = createRater(ACC_NUMBER().accountNumber().concat("1"));

        var actualRatingForPolitician = BuilderFactory.createPolRating(Score.of(5.99323), raterWhoRates, politician);

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

    @Test
    public void shouldReturnOptionalOfEmptyWhenRatingIsNotFoundInDatabase() throws Exception{
        var service = new RatingService(ratingRepo, polRepo, new DefaultRateLimitDomainService(rateLimitRepo));

        assertThat(service.findById(ACC_NUMBER()))
                .isEmpty();
    }

    @Test
    public void ratingShouldBeInDatabaseAfterRatingAPolitician() throws Exception{
        //GIVEN
        var rater = createRater(ACC_NUMBER().accountNumber());

        var justToIncreaseSize = BuilderFactory.createPolRating(Score.of(1), rater, null);

        PresidentialPolitician politician = new PresidentialBuilder(new PoliticiansBuilder(POL_NUMBER())
                .setFirstName("Fake")
                .setPoliticiansRating(List.of(justToIncreaseSize))
                .setRating(new Rating(1D, AverageRating.of(valueOf(1)))))
                .build();

        var actualRatingForPolitician = BuilderFactory.createPolRating(Score.of(5.99323), rater, politician);

        var service = new RatingService(ratingRepo, polRepo, new DefaultRateLimitDomainService(rateLimitRepo));

        //WHEN
        polRepo.save(politician);
        service.saveRatings(actualRatingForPolitician);

        Optional<PoliticiansRating> ratingQueried = service.findById(String.valueOf(actualRatingForPolitician.getId()));

        //THEN
        assertThat(ratingQueried)
                .isPresent();
        assertThat(ratingQueried.get())
                .isEqualTo(actualRatingForPolitician);
    }

}
