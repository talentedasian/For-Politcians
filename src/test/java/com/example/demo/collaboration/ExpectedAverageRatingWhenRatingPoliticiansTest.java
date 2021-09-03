package com.example.demo.collaboration;

import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.InMemoryPoliticianAdapterRepo;
import com.example.demo.domain.InMemoryRatingAdapterRepo;
import com.example.demo.domain.NumberTestFactory;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.Politicians;
import org.junit.jupiter.api.BeforeEach;

public class ExpectedAverageRatingWhenRatingPoliticiansTest {

    final String FIRST_NAME = "Rodrigo";
    final String LAST_NAME = "Duterte";

    PoliticiansRepository polRepo;
    RateLimitRepository rateLimitRepo;
    RatingRepository ratingRepo;

    Politicians.PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(NumberTestFactory.POL_NUMBER().politicianNumber())
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setPoliticiansRating(null)
            .setRating(new Rating(0D, 0D));

    @BeforeEach
    public void setup() {
        polRepo = new InMemoryPoliticianAdapterRepo();

        rateLimitRepo = new InMemoryRateLimitRepository();

        ratingRepo = new InMemoryRatingAdapterRepo();
    }


}
