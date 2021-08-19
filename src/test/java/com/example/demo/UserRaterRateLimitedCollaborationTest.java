package com.example.demo;

import com.example.demo.adapter.in.service.RatingService;
import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.*;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserRaterRateLimitedCollaborationTest {

    RateLimitRepository rateLimitRepository;

    Politicians politician = new Politicians.PoliticiansBuilder("dummy")
            .setFirstName("Random")
            .setLastName("Name")
            .setPoliticiansRating(null)
            .setRating(new Rating(0D, 0D))
            .build();

    final String NAME = "Any Name Really";

    final String ID = "123456";
    final String POLITICIAN_NUMBER = politician.retrievePoliticianNumber();

    final String ACCOUNT_NUMBER = new AccountNumber(ID, FacebookUserRaterNumberImplementor.with(NAME, ID)).retrieveAccountNumber();

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
                .setAccountNumber(ID)
                .setName(NAME)
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimitRepo(rateLimitRepository)
                .build();

        var rating = new PoliticiansRating.Builder()
                .setRating(0.D)
                .setPolitician(politician)
                .setRepo(rateLimitRepository)
                .setRater(rater)
                .build();

        rateLimitRepository.save(new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, LocalDate.now()));

        Assertions.assertThrows(UserRateLimitedOnPoliticianException.class, () -> service.saveRatings(rating));
    }

    @Test
    public void shouldSaveRateLimitWithUsersAccountNumberAndPoliticianNumberThatIsRated() throws UserRateLimitedOnPoliticianException {

        when(polRepo.findByPoliticianNumber(anyString())).thenReturn(Optional.of(politician));

        var service = new RatingService(ratingRepo, polRepo, rateLimitRepository);

        var rater = new UserRater.Builder()
                .setAccountNumber(ID)
                .setName("Any Name Really")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimitRepo(rateLimitRepository)
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
