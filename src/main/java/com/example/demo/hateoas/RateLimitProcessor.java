package com.example.demo.hateoas;

import com.example.demo.adapter.dto.RateLimitJpaDto;
import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.in.service.RateLimitingService;
import com.example.demo.adapter.in.web.RatingsController;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.server.RepresentationModelProcessor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class RateLimitProcessor implements RepresentationModelProcessor<RateLimitJpaDto>{

	private final RateLimitingService rateLimitService;
	
	public RateLimitProcessor(RateLimitingService rateLimitService) {
		super();
		this.rateLimitService = rateLimitService;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public RateLimitJpaDto process(RateLimitJpaDto model) {
		Link ratingLink = linkTo(methodOn(RatingsController.class)
				.getRatingByRaterAccountNumber(model.getAccountNumber()))
				.withRel("rating-account-number");
		
		if (isRateLimited(model)) {			
			return model.add(ratingLink);
		}
			
		Link affordance = null;
		try {
			affordance = Affordances.of(ratingLink)
				.afford(POST)
				.withInput(AddRatingDTORequest.class)
				.withInputMediaType(APPLICATION_JSON)
				.withTarget(linkTo(methodOn(RatingsController.class).saveRating(null, null)).withRel("rate"))
				.build()
				.toLink();
		} catch (UserRateLimitedOnPoliticianException e) {
			LoggerFactory.getLogger(RatingProcessor.class).info("""
					Exception not supposed to throw. Either a problem with our 
					code or in the Spring Hateoas Framework
					""", e);
		}
		
		return model.add(affordance);
	}
	
	private boolean isRateLimited(RateLimitJpaDto entity) {
		return !rateLimitService.isNotRateLimited(entity.getAccountNumber(),
				entity.getPoliticianNumber());
	}

}
