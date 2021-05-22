package com.example.demo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.exceptions.RatingsNotFoundException;
import com.example.demo.jwt.JwtProviderHttpServletRequest;
import com.example.demo.model.Politicians;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;

import io.jsonwebtoken.Claims;

@Service
public class RatingService {

	private final RatingRepository ratingRepo;
	private final PoliticiansRepository politicianRepo;

	public RatingService(RatingRepository ratingRepo, PoliticiansRepository politicianRepo) {
		this.ratingRepo = ratingRepo;
		this.politicianRepo = politicianRepo;
	}
	
	@Transactional(readOnly = true)
	public PoliticiansRating findById(String id) {
		PoliticiansRating rating = ratingRepo.findById(Integer.valueOf(id))
				.orElseThrow(() -> new RatingsNotFoundException("No rating found by Id"));
		
		return rating;
	}
	
	@Transactional
	public PoliticiansRating saveRatings(AddRatingDTORequest dto, HttpServletRequest req) {
		Politicians politician = politicianRepo.findByLastNameAndFirstName(dto.getPoliticianLastName(), dto.getPoliticianFirstName())
				.orElseThrow(() -> new PoliticianNotFoundException("No policitian found by " + dto.getPoliticianFirstName() + "\s" + dto.getPoliticianLastName()));
		
		PoliticalParty politicalParty = PoliticalParty.mapToPoliticalParty(dto.getPoliticalParty());
		
		Claims jwt = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		
		var rating = new PoliticiansRating();
		rating.calculateRating(dto.getRating().doubleValue());
		rating.calculatePolitician(politician);
		rating.calculateRater(jwt.getSubject(), jwt.getId(), politicalParty);
		
		politician.calculateListOfRaters(rating);
		
		politicianRepo.save(politician);
		ratingRepo.save(rating);
		
		return rating;
	}
	
	@Transactional(readOnly = true)
	public List<PoliticiansRating> findRatingsByFacebookEmail(String email) {
		List<PoliticiansRating> ratingsByRater = ratingRepo.findByRater_Email(email);
		if (ratingsByRater.isEmpty()) {
			throw new RatingsNotFoundException("No rating found by Rater"); 
		}
		
		return ratingsByRater;
	}

}
