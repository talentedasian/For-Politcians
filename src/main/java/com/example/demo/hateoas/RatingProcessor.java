package com.example.demo.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;

import com.example.demo.controller.RatingsController;
import com.example.demo.dto.RatingDTO;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.service.RateLimitingService;

public class RatingProcessor implements RepresentationModelProcessor<EntityModel<RatingDTO>>{

	private final RateLimitingService rateLimitService;
	
	public RatingProcessor(RateLimitingService rateLimitService) {
		super();
		this.rateLimitService = rateLimitService;
	}

	@Override
	public EntityModel<RatingDTO> process(EntityModel<RatingDTO> model) {
		var rating = model.getContent();
		if (isRateLimited(rating)) {
			System.out.println("ulol potanginamo todamax");
			return model;
		}
		
		try {
			model.add(linkTo(methodOn(RatingsController.class)
					.getRatingById(model.getContent().getRater().getUserAccountNumber()))
				.withRel("rating")
				.andAffordance(afford(methodOn(RatingsController.class)
					.saveRating(null, null))));
		} catch (UserRateLimitedOnPoliticianException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	
	private boolean isRateLimited(RatingDTO entity) {
		return !rateLimitService.isNotRateLimited(entity.getRater().getUserAccountNumber(),
				entity.getPolitician().getId());
	}

}
