package com.example.demo.adapter.in.service;

import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class RatingService {

	private final RatingRepository ratingRepo;
	private final PoliticiansService politicianService;
	private final RateLimitingService rateLimitingService;
	private final RateLimitRepository rateLimitRepository;

	public RatingService(RatingRepository ratingRepo, PoliticiansRepository politicianRepo,
						 RateLimitRepository rateLimitRepo) {
		this.ratingRepo = ratingRepo;
		this.politicianService = new PoliticiansService(politicianRepo);
		this.rateLimitingService = new RateLimitingService(rateLimitRepo);
		this.rateLimitRepository = rateLimitRepo;
	}
	
	@Transactional(readOnly = true)
	public Optional<PoliticiansRating> findById(String id) {
		return ratingRepo.findById(id);
	}
	
	@Transactional
	public PoliticiansRating saveRatings(PoliticiansRating rating) throws UserRateLimitedOnPoliticianException {
		Politicians politician = politicianService.findPoliticianByNumber(rating.getPolitician().retrievePoliticianNumber())
				.orElseThrow(PoliticianNotFoundException::new);

		String polNumber = politician.retrievePoliticianNumber();

		rating.ratePolitician();
		
		politicianService.updatePolitician(politician);
		PoliticiansRating savedRating = ratingRepo.save(rating);
		
		return savedRating;
	}

	@Transactional(readOnly = true)
	public List<PoliticiansRating> findRatingsByFacebookEmail(String email) {
		return ratingRepo.findByRater_Email(email);
	}
	
	@Transactional(readOnly = true)
	public List<PoliticiansRating> findRatingsByAccountNumber(String accNumber) {
		return ratingRepo.findByRater_UserAccountNumber(accNumber);
	}
	
	@Transactional
	public void deleteById(Integer id) {
		ratingRepo.deleteById(id);
	}
	
	@Transactional
	public void deleteByAccountNumber(String accountNumber) {
		ratingRepo.deleteByRater_UserAccountNumber(accountNumber);
	}
	
}
