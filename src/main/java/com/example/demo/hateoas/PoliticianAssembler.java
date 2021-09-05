package com.example.demo.hateoas;

import com.example.demo.adapter.dto.PoliticianDto;
import com.example.demo.adapter.in.web.PoliticianController;
import com.example.demo.adapter.in.web.RatingsController;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PoliticianAssembler implements SimpleRepresentationModelAssembler<PoliticianDto>{

	@Override
	public void addLinks(EntityModel<PoliticianDto> resource) {
		PoliticianDto content = resource.getContent();

		resource.add(linkTo(methodOn(PoliticianController.class).politicianById(content.getId()))
				.withSelfRel());

		try {
			resource.add(linkTo(methodOn(RatingsController.class).saveRating(null, null))
					.withRel("rate-politician"));
		} catch (UserRateLimitedOnPoliticianException e) {
			LoggerFactory.getLogger("Politician-Assembler").warn("Method not supposed to throw this exception");
		}

	}

	@Override
	public void addLinks(CollectionModel<EntityModel<PoliticianDto>> resources) {
		// TODO Auto-generated method stub
	}

}
