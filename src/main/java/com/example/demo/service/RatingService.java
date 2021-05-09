package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.exceptions.RatingsNotFoundException;
import com.example.demo.jwt.JwtProviderHttpServletRequest;
import com.example.demo.model.Politicians;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.model.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;

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
		Politicians politician = politicianRepo.findByName(dto.getPoliticianName())
				.orElseThrow(() -> new PoliticianNotFoundException("No policitian found by " + dto.getPoliticianName()));
		
		PoliticalParty politicalParty = null;
		for (PoliticalParty politicalParties : PoliticalParty.values()) {
			if (dto.getPoliticialParty().equalsIgnoreCase(politicalParties.toString())) {
				politicalParty = politicalParties;
			}
		}
		
		politician.setTotalRating(politician.getTotalRating() + dto.getRating().doubleValue());
		politician.setRating(politician.getTotalRating() / Double.valueOf(String.valueOf(politician.getPoliticiansRating().size() + 1)));
		
		politicianRepo.save(politician);
		
		var rating = new PoliticiansRating();
		
		var userRater = new UserRater(JwtProviderHttpServletRequest.decodeJwt(req).getBody().getId(),
				politicalParty);
		rating.setPolitician(politician);
		rating.setRater(userRater);
		rating.setRating(dto.getRating().doubleValue());
		
		ratingRepo.save(rating);
		
		return rating;
	}

}
