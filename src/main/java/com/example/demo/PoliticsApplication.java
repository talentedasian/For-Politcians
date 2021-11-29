package com.example.demo;

import com.example.demo.adapter.out.jpa.PoliticiansJpaEntity;
import com.example.demo.adapter.out.jpa.PoliticiansRatingJpaEntity;
import com.example.demo.adapter.out.repository.PoliticiansJpaRepository;
import com.example.demo.adapter.out.repository.RatingJpaRepository;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.Score;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.Politicians.PoliticiansBuilder;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.enums.PoliticalParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PoliticsApplication implements CommandLineRunner{

	@Autowired
	PoliticiansJpaRepository repo;
	@Autowired RatingJpaRepository ratingRepo;

	public static void main(String[] args) {
		SpringApplication.run(PoliticsApplication.class, args);
	}
	
	@Bean
	public RestTemplate template() {
		RestTemplate restTemplate = new RestTemplate();
		
		return restTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		Politicians politician = new PoliticiansBuilder(PoliticianNumber.of(PoliticianNumber.pattern))
				.setFirstName("Random")
				.setLastName("Name")
				.setAverageRating(AverageRating.of("7.431"))
				.setTotalCount(100000)
				.build();
			PresidentialPolitician presidential = new PresidentialBuilder(politician)
				.setMostSignificantLawPassed("Rice Tarification Law")
				.build();

		repo.save(PoliticiansJpaEntity.from(presidential));

		var rater = new UserRater.Builder()
				.setEmail("test@gmail.com")
				.setPoliticalParty(PoliticalParty.DDS)
				.setName("Jmeter Test")
				.setAccountNumber("FLOPM-00000000000000")
				.build();
		var rating = new PoliticiansRating.Builder()
				.setRating(Score.of(7.4313))
				.setRater(rater)
				.setPolitician(presidential)
				.build();
		List<PoliticiansRatingJpaEntity> rateList = new ArrayList<>();
		for (int i = 0; i < 100001; i++) {
			if (i % 1000 == 0) {
				ratingRepo.saveAll(rateList);
				rateList.clear();
			}

			rateList.add(PoliticiansRatingJpaEntity.from(rating));
		}
	}

}
