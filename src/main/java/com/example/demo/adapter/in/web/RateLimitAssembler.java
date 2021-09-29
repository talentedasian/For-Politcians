package com.example.demo.adapter.in.web;

import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.in.web.dto.RateLimitDto;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RateLimitAssembler implements SimpleRepresentationModelAssembler<RateLimitDto> {

    @Override
    public void addLinks(EntityModel<RateLimitDto> resource) {
        RateLimitDto content = resource.getContent();
        if (content == null) return;

        resource.add(linkTo(methodOn(PoliticianController.class)
                .politicianById(content.getPoliticianNumber()))
                .withRel("politician"));

        Link selfLink = null;
        
        try {
            selfLink = linkTo(methodOn(RateLimitController.class)
                    .findRateLimitOnCurrentUser(content.getPoliticianNumber(), null))
                    .withSelfRel();
        } catch (UserRateLimitedOnPoliticianException e) {
            e.printStackTrace();
        }

        var rateLimit = content.toRateLimit();
        if (rateLimit.isNotRateLimited()) {
            try {
                Link linkToRatePolitician = Affordances.of(selfLink)
                        .afford(HttpMethod.POST)
                        .withTarget(linkTo(methodOn(RatingsController.class).saveRating(null, null)).withSelfRel())
                        .withInput(AddRatingDTORequest.class)
                        .withInputMediaType(MediaType.APPLICATION_JSON)
                        .toLink();

                resource.add(linkToRatePolitician);
            } catch (UserRateLimitedOnPoliticianException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<RateLimitDto>> resources) {

    }
}
