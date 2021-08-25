package com.example.demo;

import com.example.demo.adapter.in.service.RatingService;
import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.NumberTestFactory;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.*;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import com.example.demo.domain.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.domain.userRaterNumber.facebook.FacebookAccountNumberCalculator;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserRaterRateLimitedCollaborationTest {

    RateLimitRepository rateLimitRepository;

    PoliticiansBuilder politicianBuilder = new PoliticiansBuilder(NumberTestFactory.POL_NUMBER())
            .setFirstName("Random")
            .setLastName("Name")
            .setPoliticiansRating(null)
            .setRating(new Rating(0D, 0D));

    Politicians politician = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder)
            .build();

    final String NAME = "Any Name Really";

    final String FACEBOOK_ID = "123456";
    final String POLITICIAN_NUMBER = politician.retrievePoliticianNumber();

    AbstractUserRaterNumber accNumberCalc = FacebookAccountNumberCalculator.with(NAME, FACEBOOK_ID);

    final String ACCOUNT_NUMBER = new AccountNumber(accNumberCalc.calculateEntityNumber().getAccountNumber()).accountNumber();

    @Mock RatingRepository ratingRepo;
    @Mock PoliticiansRepository polRepo;

    @BeforeEach
    public void setup() {
        rateLimitRepository = new InMemoryRateLimitRepository();
    }

    @Test
    public void shouldThrowAnExceptionBecauseUserIsRateLimited() {
        when(polRepo.findByPoliticianNumber(anyString())).thenReturn(Optional.of(politician));

        var service = new RatingService(ratingRepo, polRepo, rateLimitRepository);

        var rater = new UserRater.Builder()
                .setAccountNumber(ACCOUNT_NUMBER)
                .setName(NAME)
                .setPoliticalParty(PoliticalParty.DDS)
                .build();

        var rating = new PoliticiansRating.Builder()
                .setRating(0.D)
                .setPolitician(politician)
                .setRepo(rateLimitRepository)
                .setRater(rater)
                .build();

        rateLimitRepository.save(new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, LocalDate.now()));

        assertThrows(UserRateLimitedOnPoliticianException.class, () -> service.saveRatings(rating));
    }

    @Test
    public void shouldSaveRateLimitWithUsersAccountNumberAndPoliticianNumberThatIsRated() throws UserRateLimitedOnPoliticianException {
        when(polRepo.findByPoliticianNumber(anyString())).thenReturn(Optional.of(politician));

        var service = new RatingService(ratingRepo, polRepo, rateLimitRepository);

        var rater = new UserRater.Builder()
                .setAccountNumber(ACCOUNT_NUMBER)
                .setName("Any Name Really")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        var rating = new PoliticiansRating.Builder()
                .setRating(0.D)
                .setPolitician(politician)
                .setRepo(rateLimitRepository)
                .setRater(rater)
                .build();

        rateLimitRepository.save(new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, LocalDate.now().minusDays(8)));
        service.saveRatings(rating);

        var rateLimit = rateLimitRepository.findUsingIdAndPoliticianNumber(ACCOUNT_NUMBER, POLITICIAN_NUMBER);

        assertAll(
                () -> assertThat(rateLimit)
                        .isNotEmpty(),
                () -> assertThat(rateLimit.get().daysLeftOfBeingRateLimited())
                        .isEqualTo(7));
    }

}
