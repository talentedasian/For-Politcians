package com.example.demo.adapter.in.web;

import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.in.service.RatingServiceAdapter;
import com.example.demo.adapter.web.dto.RatingDTO;
import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.hateoas.RatingAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingsController {
	
	private final RatingServiceAdapter ratingService;
	private final RatingAssembler assembler;

	public RatingsController(RatingServiceAdapter ratingService, RatingAssembler assembler) {
		this.ratingService = ratingService;
		this.assembler = assembler;
	}

	@Operation(security = { @SecurityRequirement(name = "add-rating") })
	@PostMapping("/rating")
	public ResponseEntity<EntityModel<RatingDTO>> saveRating(@Valid @RequestBody AddRatingDTORequest request, HttpServletRequest req) throws UserRateLimitedOnPoliticianException {
		RatingDTO politicianRatingSaved = ratingService.saveRatings(request, req);
		
		EntityModel<RatingDTO> response = assembler.toModel(politicianRatingSaved);
		
		return new ResponseEntity<EntityModel<RatingDTO>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/rating/{id}")
	public ResponseEntity<EntityModel<RatingDTO>> getRatingById(@PathVariable String id) {
		RatingDTO politicianRatingQueried = ratingService.findUsingId(id);

		EntityModel<RatingDTO> response = assembler.toModel(politicianRatingQueried);
		
		return new ResponseEntity<EntityModel<RatingDTO>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/ratings")
	public ResponseEntity<CollectionModel<EntityModel<RatingDTO>>> getRatingByRaterEmail(@RequestParam String email) {
		List<RatingDTO> politicianRatingQueried = ratingService.findRatingsUsingFacebookEmail(email);
		
		CollectionModel<EntityModel<RatingDTO>> response = assembler.toCollectionModel(politicianRatingQueried);
		
		return new ResponseEntity<CollectionModel<EntityModel<RatingDTO>>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/ratings/{accNumber}")
	public ResponseEntity<CollectionModel<EntityModel<RatingDTO>>> getRatingByRaterAccountNumber(@PathVariable String accNumber) {
		if (!AccountNumber.isValid(accNumber)) throw new InappropriateAccountNumberException(accNumber);
		List<RatingDTO> politicianRatingQueried = ratingService.findRatingsUsingAccountNumber(accNumber);

		CollectionModel<EntityModel<RatingDTO>> response = assembler.toCollectionModel(politicianRatingQueried);

		return new ResponseEntity<CollectionModel<EntityModel<RatingDTO>>>(response, HttpStatus.OK);
	}

}
