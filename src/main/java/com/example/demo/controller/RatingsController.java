package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RatingDTO;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.dtomapper.RatingDtoMapper;
import com.example.demo.dtomapper.interfaces.RatingDTOMapper;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.service.RatingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/ratings")
public class RatingsController {
	
	private final RatingService ratingService;
	private RatingDTOMapper mapper;
	
	@Autowired(required = false)
	public RatingsController(RatingService ratingService, Optional<RatingDTOMapper> mapper) {
		this.ratingService = ratingService;
		mapper.ifPresent(dtoMapper -> {this.mapper = dtoMapper;});
	}

	@Operation(security = { @SecurityRequirement(name = "add-rating") })
	@PostMapping("/rating")
	public ResponseEntity<RatingDTO> saveRating(@Valid @RequestBody AddRatingDTORequest request, HttpServletRequest req) {
		PoliticiansRating politicianRatingSaved = ratingService.saveRatings(request, req);
		
		mapper = new RatingDtoMapper();
		
		RatingDTO politicianRating = mapper.mapToDTO(politicianRatingSaved);
		
		return new ResponseEntity<RatingDTO>(politicianRating, HttpStatus.CREATED);
	}
	
	@GetMapping("/rating/{id}")
	public ResponseEntity<RatingDTO> getRatingById(@PathVariable String id) {
		PoliticiansRating politicianRatingQueried = ratingService.findById(id);
		
		mapper = new RatingDtoMapper();
		
		RatingDTO politicianRating = mapper.mapToDTO(politicianRatingQueried);
		
		return new ResponseEntity<RatingDTO>(politicianRating, HttpStatus.OK);
	}
	
	@GetMapping("/rating")
	public ResponseEntity<List<RatingDTO>> getRatingByRater(@RequestParam String email) {
		List<PoliticiansRating> politicianRatingQueried = ratingService.findRatingsByFacebookEmail(email);
		
		mapper = new RatingDtoMapper();
		List<RatingDTO> politicianRating = mapper.mapToDTO(politicianRatingQueried);
		
		
		return new ResponseEntity<List<RatingDTO>>(politicianRating, HttpStatus.OK);
	}

}
