package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RatingDTO;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.dtomapper.RatingDtoMapper;
import com.example.demo.dtomapper.interfaces.DTOMapper;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.service.RatingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/ratings")
public class RatingsController {
	
	private final RatingService ratingService;
	
	public RatingsController(RatingService ratingService) {
		this.ratingService = ratingService;
	}

	
	@PostMapping("/add-rating")
	public ResponseEntity<RatingDTO> saveRating(@Valid @RequestBody AddRatingDTORequest request, HttpServletRequest req) {
		PoliticiansRating politicianRatingSaved = ratingService.saveRatings(request, req);
		
		DTOMapper<RatingDTO, PoliticiansRating> mapper = new RatingDtoMapper();
		
		RatingDTO politicianRating = mapper.mapToDTO(politicianRatingSaved);
		
		return new ResponseEntity<RatingDTO>(politicianRating, HttpStatus.CREATED);
	}
	
	@GetMapping("/ratingById")
	public ResponseEntity<RatingDTO> getRatingById(@RequestParam String id) {
		PoliticiansRating politicianRatingQueried = ratingService.findById(id);
		
		DTOMapper<RatingDTO, PoliticiansRating> mapper = new RatingDtoMapper();
		
		RatingDTO politicianRating = mapper.mapToDTO(politicianRatingQueried);
		
		return new ResponseEntity<RatingDTO>(politicianRating, HttpStatus.OK);
	}
	
	@GetMapping("/ratingByRater")
	public ResponseEntity<List<RatingDTO>> getRatingByRater(@RequestParam String email) {
		List<PoliticiansRating> politicianRatingQueried = ratingService.findRatingsByFacebookEmail(email);
		List<RatingDTO> politicianRating = new ArrayList<>();
		
		DTOMapper<RatingDTO, PoliticiansRating> mapper = new RatingDtoMapper();
		
		for (PoliticiansRating politiciansRatings : politicianRatingQueried) {
			politicianRating.add(mapper.mapToDTO(politiciansRatings));
		}
		
		return new ResponseEntity<List<RatingDTO>>(politicianRating, HttpStatus.OK);
	}

}
