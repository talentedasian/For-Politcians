package com.example.demo.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.example.demo.adapter.in.web.PoliticianController;
import com.example.demo.adapter.in.web.RatingsController;
import com.example.demo.adapter.dto.RatingDTO;
import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.adapter.in.service.RateLimitingService;

public class RatingProcessor implements RepresentationModelProcessor<EntityModel<RatingDTO>>{

	private final RateLimitingService rateLimitService;
	
	public RatingProcessor(RateLimitingService rateLimitService) {
		super();
		this.rateLimitService = rateLimitService;
	}

	@SuppressWarnings("deprecation")
	@Override
	public EntityModel<RatingDTO> process(EntityModel<RatingDTO> model) {
		var rating = model.getContent();
		
		Link linkToPolitician = linkTo(methodOn(PoliticianController.class).politicianById(rating.getPolitician().getId()))
				.withRel("politician");
		
		if (isRateLimited(rating)) {
			return model.add(linkToPolitician);
		}
		
		Link affordance = null;
		try {
			affordance = Affordances.of(linkToPolitician)
					.afford(HttpMethod.POST)
					.withInput(AddRatingDTORequest.class)
					.withInputMediaType(MediaType.APPLICATION_JSON)
					.withTarget(linkTo(methodOn(RatingsController.class).saveRating(null, null)).withRel("rate"))
					.build()
					.toLink();
		} catch (UserRateLimitedOnPoliticianException e) {
			LoggerFactory.getLogger(RatingProcessor.class).info("""
					Exception not supposed to throw. Either a problem with our 
					code or in the Spring Hateoas Framework
					""", e);
		}

		model.add(affordance);
		
		return model;
	}
	
	private boolean isRateLimited(RatingDTO entity) {
		return !rateLimitService.isNotRateLimited(entity.getRater().getUserAccountNumber(),
				entity.getPolitician().getId());
	}

}
