package com.example.demo.adapter.out.jpa;

import com.example.demo.adapter.out.repository.PoliticiansJpaRepository;
import com.example.demo.adapter.out.repository.RatingJpaRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.adapter.out.repository.RatingRepositoryJpaAdapter;
import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.baseClasses.BuilderFactory;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.Score;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.entities.Politicians.PoliticiansBuilder;
import com.example.demo.domain.entities.PoliticiansRating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import testAnnotations.DatabaseTest;

import java.math.BigDecimal;

import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class RatingJpaAdapterRepoTest extends BaseClassTestsThatUsesDatabase {

    @Autowired PoliticiansJpaRepository polJpaRepository;

    @Autowired RatingJpaRepository ratingJpaRepository;

    RatingRepository ratingRepo;

    @BeforeEach
    public void setup() {
        ratingRepo = new RatingRepositoryJpaAdapter(ratingJpaRepository);
    }

    final String FIRST_NAME = "Rodrigo";
    final String LAST_NAME = "Duterte";

    PoliticiansBuilder politicianBuilder = new PoliticiansBuilder(NumberTestFactory.POL_NUMBER())
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setPoliticiansRating(null)
            .setTotalRating(BigDecimal.ZERO)
            .setAverageRating(AverageRating.ONE);

    PresidentialPolitician presidential = new PresidentialBuilder(politicianBuilder).build();


    @Test public void //TRYING THIS FORMAT OUT
    should_convert_and_save_to_database() throws Exception{
        PoliticiansJpaEntity savedPolitician = polJpaRepository.saveAndFlush(PoliticiansJpaEntity.from(presidential));

        Score scoreForPolitician = Score.of("1");
        var rating = new PoliticiansRating.Builder()
                .setRater(BuilderFactory.createRater(ACC_NUMBER().accountNumber()))
                .setPolitician(savedPolitician.toPoliticians())
                .setRating(scoreForPolitician)
                .build();

        PoliticiansRating savedRating = ratingRepo.save(rating);

        assertThat(savedRating.id())
                .isNotNull();

        assertThat(savedRating.whoRated())
                .isEqualTo(rating.whoRated());

        assertThat(savedRating.score())
                .isEqualTo(scoreForPolitician.rating());

        assertThat(savedRating.whoWasRated())
                .isEqualTo(presidential);
    }

    @Test
    public void testAverageFunctionQuery() throws Exception{
        PoliticiansJpaEntity savedPolitician = polJpaRepository.saveAndFlush(PoliticiansJpaEntity.from(presidential));

        Score scoreForPolitician = Score.of("1");
        var rating = new PoliticiansRating.Builder()
                .setRater(BuilderFactory.createRater(ACC_NUMBER().accountNumber()))
                .setPolitician(savedPolitician.toPoliticians())
                .setRating(scoreForPolitician)
                .build();
        var rating2 = new PoliticiansRating.Builder()
                .setRater(BuilderFactory.createRater(ACC_NUMBER().accountNumber()))
                .setPolitician(savedPolitician.toPoliticians())
                .setRating(Score.of("9.2234423"))
                .build();

        ratingRepo.save(rating);
        ratingRepo.save(rating2);
        assertThat(ratingJpaRepository.calculateRating(savedPolitician.getId()))
                .isEqualTo(1);
    }

}
