package com.example.demo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.exceptions.RatingsNotFoundException;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.jwt.JwtProviderHttpServletRequest;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.model.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;

import io.jsonwebtoken.Claims;

@Service
public class RatingService {

	private final RatingRepository ratingRepo;
	private final PoliticiansRepository politicianRepo;
	private final RateLimitingService rateLimitService;

	public RatingService(RatingRepository ratingRepo, PoliticiansRepository politicianRepo, 
			RateLimitingService rateLimitService) {
		this.ratingRepo = ratingRepo;
		this.politicianRepo = politicianRepo;
		this.rateLimitService = rateLimitService;
	}
	
	@Transactional(readOnly = true)
	public PoliticiansRating findById(String id) {
		PoliticiansRating rating = ratingRepo.findById(Integer.valueOf(id))
				.orElseThrow(() -> new RatingsNotFoundException("No rating found by " + id));
		
		return rating;
	}
	
	@Transactional
	public PoliticiansRating saveRatings(AddRatingDTORequest dto, HttpServletRequest req) throws UserRateLimitedOnPoliticianException {
		Politicians politician = politicianRepo.findByPoliticianNumber(dto.getId())
				.orElseThrow(() -> new PoliticianNotFoundException("No policitian found by id"));
		politician.setRepo(ratingRepo);
		
		Claims jwt = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		String stringJwt = JwtProviderHttpServletRequest.extractJwtFromReq(req);
		
		AbstractUserRaterNumber accountNumberImplementor = FacebookUserRaterNumberImplementor.with(jwt.get("name", String.class), jwt.getId());
		String accountNumber = accountNumberImplementor.calculateEntityNumber().getAccountNumber();
		String polNumber = politician.getPoliticianNumber();
		
		var rating = new PoliticiansRating();
		rating.calculateRating(dto.getRating().doubleValue());
		rating.calculatePolitician(politician);
		rating.calculateRater(jwt.getSubject(), jwt.getId(), dto.getPoliticalParty(), accountNumber, rateLimitService);
		
		/*
		 * check whether the user is currently not allowed to rate
		 * a politician. The timeout/rate limit is within a week.
		 */
		if (!canRate(rating.getRater(), stringJwt, polNumber)) {
			Long daysLeft = rateLimitService.daysLeftOfBeingRateLimited(accountNumber, polNumber).longValue();
			
			throw new UserRateLimitedOnPoliticianException("User is rate limited on politician with " + daysLeft + " days left", 
					daysLeft);
		}
		
		/*
		 * save the rate limit in the database to be fetched whenever a user
		 * wants to rate a politician(see if statement above). this method already 
		 * deletes the existing rate limit for the user.
		 */
		rateLimitService.rateLimitUser(accountNumber, polNumber);
		
		
		politician.calculateListOfRaters(rating);
		
		politicianRepo.save(politician);
		PoliticiansRating savedRating = ratingRepo.save(rating);
		
		return savedRating;
	}
	
	private boolean canRate(UserRater rater, String jwt, String polNumber) {
		return rater.canRate(jwt, polNumber);
	}
	
	@Transactional(readOnly = true)
	public List<PoliticiansRating> findRatingsByFacebookEmail(String email) {
		List<PoliticiansRating> ratingsByRater = ratingRepo.findByRater_Email(email);
		if (ratingsByRater.isEmpty()) {
			throw new RatingsNotFoundException("No rating found by Rater " + email); 
		}
		
		return ratingsByRater;
	}
	
	@Transactional(readOnly = true)
	public List<PoliticiansRating> findRatingsByAccountNumber(String accNumber) {
		List<PoliticiansRating> ratingsByRater = ratingRepo.findByRater_UserAccountNumber(accNumber);
		if (ratingsByRater.isEmpty()) {
			throw new RatingsNotFoundException("No rating found by " + accNumber); 
		}
		
		return ratingsByRater;
	}
	
	@Transactional
	public boolean deleteById(Integer id) {
		if (ratingRepo.existsById(id)) {
			ratingRepo.deleteById(id);
			return true;
		}
		
		return false;
	}
	
	@Transactional
	public boolean deleteByAccountNumber(String accountNumber) {
		if (ratingRepo.existsByRater_UserAccountNumber(accountNumber)) {
			ratingRepo.deleteByRater_UserAccountNumber(accountNumber);
			return true;
		}
		
		return false;
	}
	
}
