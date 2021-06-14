package com.example.demo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.exceptions.RateLimitedException;
import com.example.demo.exceptions.RatingsNotFoundException;
import com.example.demo.jwt.JwtProviderHttpServletRequest;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.redis.RateLimiter;
import com.example.demo.model.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.model.userRaterNumber.FacebookUserRaterNumberImplementor;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;

import io.jsonwebtoken.Claims;

@Service
public class RatingService {

	private final RatingRepository ratingRepo;
	private final PoliticiansRepository politicianRepo;
	private final RateLimiterService rateLimiterService;

	public RatingService(RatingRepository ratingRepo, PoliticiansRepository politicianRepo, 
			RateLimiterService rateLimiterService) {
		this.ratingRepo = ratingRepo;
		this.politicianRepo = politicianRepo;
		this.rateLimiterService = rateLimiterService;
	}
	
	@Transactional(readOnly = true)
	public PoliticiansRating findById(String id) {
		PoliticiansRating rating = ratingRepo.findById(Integer.valueOf(id))
				.orElseThrow(() -> new RatingsNotFoundException("No rating found by Id"));
		
		return rating;
	}
	
	@Transactional
	public PoliticiansRating saveRatings(AddRatingDTORequest dto, HttpServletRequest req) {
		Politicians politician = politicianRepo.findByPoliticianNumber(dto.getId())
				.orElseThrow(() -> new PoliticianNotFoundException("No policitian found by id"));
		politician.setRepo(ratingRepo);
		
		Claims jwt = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		
		AbstractUserRaterNumber accountNumberImplementor = FacebookUserRaterNumberImplementor.with(jwt.get("name", String.class), jwt.getId());
		String accountNumber = accountNumberImplementor.calculateUserAccountNumber().getAccountNumber();
		if (isUserRatedLimited(accountNumber)) {
			RateLimiter rateLimit = rateLimiterService.findRateLimitByUserAccountNumber(accountNumber).get();
			Long rateLimitTimeout = rateLimiterService.getExpirationTimeOfKey(rateLimit.getId());
			throw new RateLimitedException(rateLimitTimeout, 
					"User has " + rateLimitTimeout + " seconds left before consuming the current endpoint");
		}
		
		
		var rating = new PoliticiansRating();
		rating.calculateRating(dto.getRating().doubleValue());
		rating.calculatePolitician(politician);
		rating.calculateRater(jwt.getSubject(), jwt.getId(), dto.getPoliticalParty(), accountNumberImplementor.calculateEntityNumber().getAccountNumber());
		
		politician.calculateListOfRaters(rating);
		
		politicianRepo.save(politician);
		PoliticiansRating savedRating = ratingRepo.save(rating);
		
		return savedRating;
	}
	
	@Transactional(readOnly = true)
	public List<PoliticiansRating> findRatingsByFacebookEmail(String email) {
		List<PoliticiansRating> ratingsByRater = ratingRepo.findByRater_Email(email);
		if (ratingsByRater.isEmpty()) {
			throw new RatingsNotFoundException("No rating found by Rater"); 
		}
		
		return ratingsByRater;
	}

	private boolean isUserRatedLimited(String accountNumber) {
		return rateLimiterService.findRateLimitByUserAccountNumber(accountNumber).isPresent();
	}
	
}
