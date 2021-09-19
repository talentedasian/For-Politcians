package com.example.demo.collaboration;

import com.example.demo.adapter.in.service.RatingService;
import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.DefaultRateLimitDomainService;
import com.example.demo.domain.InMemoryPoliticianAdapterRepo;
import com.example.demo.domain.InMemoryRatingAdapterRepo;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.*;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.entities.Politicians.PoliticiansBuilder;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.domain.userRaterNumber.facebook.FacebookAccountNumberCalculator;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.demo.baseClasses.BuilderFactory.createPolRating;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class UserRaterRateLimitedCollaborationTest {

    RateLimitRepository rateLimitRepository;

    PoliticiansBuilder politicianBuilder = new PoliticiansBuilder(NumberTestFactory.POL_NUMBER())
            .setFirstName("Random")
            .setLastName("Name")
            .setPoliticiansRating(null)
            .setRating(new Rating(0D, 0D));

    Politicians politician = new PresidentialBuilder(politicianBuilder)
            .build();

    final String NAME = "Any Name Really";

    final String FACEBOOK_ID = "123456";

    AbstractUserRaterNumber accNumberCalc = FacebookAccountNumberCalculator.with(NAME, FACEBOOK_ID);

    final String ACCOUNT_NUMBER = new AccountNumber(accNumberCalc.calculateEntityNumber().getAccountNumber()).accountNumber();

    RatingRepository ratingRepo;
    PoliticiansRepository polRepo;

    @BeforeEach
    public void setup() {
        polRepo = new InMemoryPoliticianAdapterRepo();

        ratingRepo = new InMemoryRatingAdapterRepo();

        rateLimitRepository = new InMemoryRateLimitRepository();
    }

    @Test
    public void shouldThrowAnExceptionBecauseUserIsRateLimited() throws PoliticianNotPersistableException {
        polRepo.save(politician);

        var service = new RatingService(ratingRepo, polRepo, new DefaultRateLimitDomainService(rateLimitRepository));

        var rater = new UserRater.Builder()
                .setAccountNumber(ACCOUNT_NUMBER)
                .setName(NAME)
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        var rating = createPolRating(0.0D, rater, politician);

        rater.rateLimitUser(new DefaultRateLimitDomainService(rateLimitRepository), PoliticianNumber.of(politician.retrievePoliticianNumber()));

        assertThrows(UserRateLimitedOnPoliticianException.class, () -> service.saveRatings(rating));
    }

}
