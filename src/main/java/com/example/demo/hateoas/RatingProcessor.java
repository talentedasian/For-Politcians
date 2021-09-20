package com.example.demo.hateoas;

import com.example.demo.adapter.in.web.PoliticianController;
import com.example.demo.adapter.web.dto.RatingDTO;
import com.example.demo.domain.DefaultRateLimitDomainService;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.PoliticianNumber;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;

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
		
		return model.add(linkToPolitician);
	}

	private boolean canRate(RatingDTO rating) {
		return rating.getRater().toUserRater().canRate(new DefaultRateLimitDomainService(rateLimitRepo), PoliticianNumber.of(rating.getPolitician().getId()));
	}


}
