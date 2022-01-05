package com.example.demo.adapter.in.service;

import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingJpaRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.UserRateLimitService;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class RatingService {

	private final RatingRepository ratingRepo;
	private final PoliticiansRepository politiciansRepo;
	private final UserRateLimitService userRateLimitService;

	public RatingService(RatingRepository ratingRepo, PoliticiansRepository politicianRepo,
						 UserRateLimitService service) {
		this.ratingRepo = ratingRepo;
		this.politiciansRepo = politicianRepo;
		this.userRateLimitService = service;
	}
	
	@Transactional(readOnly = true)
	public Optional<PoliticiansRating> findById(String id) {
		return ratingRepo.findById(id);
	}

	@Transactional(readOnly = true)
	public Optional<PoliticiansRating> findById(AccountNumber accountNumber) {
		return ratingRepo.findById(accountNumber.accountNumber());
	}

	public PoliticiansRating saveRatings(PoliticiansRating rating, RatingJpaRepository repo) throws UserRateLimitedOnPoliticianException {
		PoliticiansRating savedRating = ratingRepo.save(rating);
		rating.ratePolitician(userRateLimitService, repo);

		politiciansRepo.update(rating.whoWasRated());

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

	public List<Politicians> findPoliticiansByAccNumber(AccountNumber accNumber, int page) {
		return ratingRepo.findPoliticiansByAccNumber(accNumber, page);
	}
}
