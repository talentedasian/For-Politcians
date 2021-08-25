package com.example.demo.hateoas;

import com.example.demo.adapter.dto.RatingDTO;
import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.in.web.PoliticianController;
import com.example.demo.adapter.in.web.RatingsController;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RatingProcessor implements RepresentationModelProcessor<EntityModel<RatingDTO>>{

	private final RateLimitRepository rateLimitRepo;

	public RatingProcessor(RateLimitRepository rateLimitRepository) {
		this.rateLimitRepo = rateLimitRepository;
	}

	@SuppressWarnings("deprecation")
	@Override
	public EntityModel<RatingDTO> process(EntityModel<RatingDTO> model) {
		var rating = model.getContent();
		
		Link linkToPolitician = linkTo(methodOn(PoliticianController.class).politicianById(rating.getPolitician().getId()))
				.withRel("politician");
		
		if (canRate(rating)) {
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

	private boolean canRate(RatingDTO rating) {
		return rating.getRater().toUserRater().canRate(rating.getPolitician().getId());
	}


}
