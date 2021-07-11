package com.example.demo.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.controller.RatingsController;
import com.example.demo.dto.RatingDTO;

@Component
public class RatingAssembler implements SimpleRepresentationModelAssembler<RatingDTO>{

	@Override
	public void addLinks(EntityModel<RatingDTO> resource) {
		resource.add(linkTo(methodOn(RatingsController.class)
				.getRatingById(resource.getContent().getId()))
			.withRel("self"));
		
		resource.add(Link.of("http://localhost:8080/rate-limit/{politicianNumber}")
			.withRel("rate-limit"));
	}

	@Override
	public void addLinks(CollectionModel<EntityModel<RatingDTO>> resources) {
		RatingDTO entity = resources.getContent().stream()
				.findAny().get()
				.getContent();
		
		resources.add(linkTo(methodOn(RatingsController.class)
				.getRatingByRaterAccountNumber(entity.getRater().getUserAccountNumber()))
			.withRel("self"));
	}

}
