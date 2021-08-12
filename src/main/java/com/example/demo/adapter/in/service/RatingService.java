package com.example.demo.adapter.in.service;

import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.in.web.jwt.JwtProviderHttpServletRequest;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.domain.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.exceptions.RatingsNotFoundException;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RatingService {

	private final RatingRepository ratingRepo;
	private final PoliticiansRepository politicianRepo;
	private final RateLimitRepository rateLimitRepo;

	@Autowired
	public RatingService(RatingRepository ratingRepo, PoliticiansRepository politicianRepo,
						 RateLimitRepository rateLimitRepo) {
		this.ratingRepo = ratingRepo;
		this.politicianRepo = politicianRepo;
		this.rateLimitRepo = rateLimitRepo;
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
		
		Claims jwt = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		
		AbstractUserRaterNumber accountNumberImplementor = FacebookUserRaterNumberImplementor.with(jwt.get("name", String.class), jwt.getId());
		String polNumber = politician.getPoliticianNumber();
		
		var rating = createPoliticiansRating(dto, politician, jwt);
		rating.calculatePolitician(politician);

		if (!canRate(rating.getRater(), polNumber)) {
			long daysLeft = rating.getRater().daysLeftToRate(polNumber);

			throw new UserRateLimitedOnPoliticianException("User is rate limited on politician with " + daysLeft + " days left", 
					daysLeft);
		}
		
		rating.ratePolitician();
		
		politicianRepo.save(politician);
		PoliticiansRating savedRating = ratingRepo.save(rating);
		
		return savedRating;
	}

	private PoliticiansRating createPoliticiansRating(AddRatingDTORequest dto, Politicians politician, Claims jwt) {
		AbstractUserRaterNumber accountNumberImplementor = FacebookUserRaterNumberImplementor.with(jwt.get("name", String.class), jwt.getId());
		String accountNumber = accountNumberImplementor.calculateEntityNumber().getAccountNumber();

		var entity = new PoliticiansRating();
		entity.calculateRating(dto.getRating().doubleValue());
		entity.setPolitician(politician);
		entity.setRater(new UserRater.Builder()
						.setAccountNumber(accountNumber)
						.setEmail(jwt.getSubject())
						.setPoliticalParty(PoliticalParty.mapToPoliticalParty(dto.getPoliticalParty()))
						.setName(jwt.get("name", String.class))
						.setRateLimitRepo(rateLimitRepo)
					.build());

		return entity;
	}
	
	private boolean canRate(UserRater rater, String polNumber) {
		return rater.canRate(polNumber);
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
