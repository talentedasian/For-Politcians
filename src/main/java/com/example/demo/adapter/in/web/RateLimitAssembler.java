package com.example.demo.adapter.in.web;

import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.in.web.dto.RateLimitDto;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.jetbrains.annotations.NotNull;
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

        addPoliticianLink(resource, content);

        Link selfLink = getSelfLink(content);

        var rateLimit = content.toRateLimit();
        if (rateLimit.isNotRateLimited()) {
            try {
                addAffordanceToRatePolitician(resource, selfLink);
            } catch (UserRateLimitedOnPoliticianException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    @NotNull
    private EntityModel<RateLimitDto> addPoliticianLink(EntityModel<RateLimitDto> resource, RateLimitDto content) {
        return resource.add(linkTo(methodOn(PoliticianController.class)
                .politicianById(content.getPoliticianNumber()))
                .withRel("politician"));
    }

    private Link getSelfLink(RateLimitDto content) {
        try {
            return linkTo(methodOn(RateLimitController.class)
                    .findRateLimitOnCurrentUser(content.getPoliticianNumber(), null))
                    .withSelfRel();
        } catch (UserRateLimitedOnPoliticianException e) {
            return null;
        }
    }

    private void addAffordanceToRatePolitician(EntityModel<RateLimitDto> resource, Link selfLink) throws UserRateLimitedOnPoliticianException {
        Link linkToRatePolitician = Affordances.of(selfLink)
                .afford(HttpMethod.POST)
                .withTarget(linkTo(methodOn(RatingsController.class).saveRating(null, null)).withSelfRel())
                .withInput(AddRatingDTORequest.class)
                .withInputMediaType(MediaType.APPLICATION_JSON)
                .toLink();

        resource.add(linkToRatePolitician);
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<RateLimitDto>> resources) {

    }
}
