package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RatingDTO;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.dtomapper.RatingDtoMapper;
import com.example.demo.dtomapper.interfaces.RatingDTOMapper;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.service.RatingService;

@RestController
@RequestMapping("/api/ratings")
public class RatingsController {
	
	private final RatingService ratingService;
	
	public RatingsController(RatingService ratingService) {
		this.ratingService = ratingService;
	}



	@PostMapping("/add-rating")
	public ResponseEntity<RatingDTO> saveRating(@Valid @RequestBody AddRatingDTORequest request, HttpServletRequest req) {
		PoliticiansRating politicianRatiingSaved = ratingService.saveRatings(request, req);
		
		RatingDTOMapper mapper = new RatingDtoMapper();
		
		RatingDTO politicianRating = mapper.mapToDTO(politicianRatiingSaved);
		
		return new ResponseEntity<RatingDTO>(politicianRating, HttpStatus.CREATED);
	}

}
