package com.example.demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.exceptions.PoliticianNotFoundException;
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
	private final OAuth2AuthorizedClientService clientService;

	public RatingService(RatingRepository ratingRepo, PoliticiansRepository politicianRepo,
			OAuth2AuthorizedClientService clientService) {
		this.ratingRepo = ratingRepo;
		this.politicianRepo = politicianRepo;
		this.clientService = clientService;
	}
	
	@Transactional(readOnly = true)
	public PoliticiansRating findById(String id) {
		PoliticiansRating rating = ratingRepo.findById(Integer.valueOf(id))
				.orElseThrow(null);
		
		return rating;
	}
	
	@Transactional(readOnly = true)
	public PoliticiansRating saveRatings(AddRatingDTORequest dto) {
		Authentication authentication =
			    SecurityContextHolder
			        .getContext()
			        .getAuthentication();

		OAuth2AuthenticationToken oauthToken =
		    (OAuth2AuthenticationToken) authentication;
			
		OAuth2AuthorizedClient client =
			    clientService.loadAuthorizedClient(
			            oauthToken.getAuthorizedClientRegistrationId(),
			            oauthToken.getName());
		Politicians politician = politicianRepo.findByName(dto.getPoliticianName())
				.orElseThrow(() -> new PoliticianNotFoundException("No policitian found by " + dto.getPoliticianName()));
		
		PoliticalParty politicalParty = null;
		for (PoliticalParty politicalParties : PoliticalParty.values()) {
			if (dto.getPoliticialParty().equals(politicalParties.toString())) {
				politicalParty = politicalParties;
			}
		}
		
		politician.setTotalRating(politician.getRating() + dto.getRating().doubleValue());
		if (politician.getPoliticiansRating().isEmpty()) {
			politician.setRating(0.00D);
		} else {
			politician.setRating(politician.getTotalRating() / politician.getPoliticiansRating().size());
		}
		
		politicianRepo.save(politician);
		
		var rating = new PoliticiansRating();
		var userRater = new UserRater(client.getPrincipalName(), politicalParty);
		rating.setPolitician(politician);
		rating.setRater(userRater);
		rating.setRating(dto.getRating().doubleValue());
		
		ratingRepo.save(rating);
		
		System.out.println(client.getPrincipalName());
		
		return rating;
	}

}
