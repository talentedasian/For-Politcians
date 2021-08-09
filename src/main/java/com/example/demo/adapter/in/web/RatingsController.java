package com.example.demo.adapter.in.web;

import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.adapter.dto.RatingDTO;
import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.dtomapper.RatingDtoMapper;
import com.example.demo.dtomapper.interfaces.RatingDTOMapper;
import com.example.demo.exceptions.RatingsNotFoundException;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.hateoas.RatingAssembler;
import com.example.demo.adapter.in.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/ratings")
public class RatingsController {
	
	private final RatingService ratingService;
	private final RatingAssembler assembler;
	private RatingDTOMapper mapper = new RatingDtoMapper();
	
	@Autowired(required = false)
	public RatingsController(RatingService ratingService, RatingAssembler assembler) {
		this.ratingService = ratingService;
		this.assembler = assembler;
	}

	@Operation(security = { @SecurityRequirement(name = "add-rating") })
	@PostMapping("/rating")
	public ResponseEntity<EntityModel<RatingDTO>> saveRating(@Valid @RequestBody AddRatingDTORequest request, HttpServletRequest req) throws UserRateLimitedOnPoliticianException {
		PoliticiansRating politicianRatingSaved = ratingService.saveRatings(request, req);
		
		RatingDTO politicianRating = mapper.mapToDTO(politicianRatingSaved);
		
		EntityModel<RatingDTO> response = assembler.toModel(politicianRating);
		
		return new ResponseEntity<EntityModel<RatingDTO>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/rating/{id}")
	public ResponseEntity<EntityModel<RatingDTO>> getRatingById(@PathVariable String id) {
		PoliticiansRating politicianRatingQueried = ratingService.findById(id);
		
		RatingDTO politicianRating = mapper.mapToDTO(politicianRatingQueried);
		
		EntityModel<RatingDTO> response = assembler.toModel(politicianRating);
		
		return new ResponseEntity<EntityModel<RatingDTO>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/ratings")
	public ResponseEntity<CollectionModel<EntityModel<RatingDTO>>> getRatingByRaterEmail(@RequestParam String email) {
		List<PoliticiansRating> politicianRatingQueried = ratingService.findRatingsByFacebookEmail(email);
		
		List<RatingDTO> politicianRating = mapper.mapToDTO(politicianRatingQueried);
		
		CollectionModel<EntityModel<RatingDTO>> response = assembler.toCollectionModel(politicianRating);
		
		return new ResponseEntity<CollectionModel<EntityModel<RatingDTO>>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/ratings/{accNumber}")
	public ResponseEntity<CollectionModel<EntityModel<RatingDTO>>> getRatingByRaterAccountNumber(@PathVariable String accNumber) {
		List<PoliticiansRating> politicianRatingQueried = ratingService.findRatingsByAccountNumber(accNumber);
		
		List<RatingDTO> politicianRating = mapper.mapToDTO(politicianRatingQueried);
		
		CollectionModel<EntityModel<RatingDTO>> response = assembler.toCollectionModel(politicianRating);
		
		return new ResponseEntity<CollectionModel<EntityModel<RatingDTO>>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/rating/{id}")
	public ResponseEntity<?> deleteRating(@PathVariable Integer id) {
		boolean delete = ratingService.deleteById(id);
		if (!delete) {
			throw new RatingsNotFoundException("Rating not found by " + id);
		}
		
		return new ResponseEntity<>(NO_CONTENT);
	}

	@DeleteMapping("/ratings/{accNumber}")
	public ResponseEntity<?> deleteRatingByAccountNumber(@PathVariable String accNumber) {
		boolean delete = ratingService.deleteByAccountNumber(accNumber);
		if (!delete) {
			throw new RatingsNotFoundException("Rating not found by " + accNumber);
		}
		
		return new ResponseEntity<>(NO_CONTENT);
	}

}
