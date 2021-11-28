package com.example.demo.hateoas;

import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.in.web.RatingsController;
import com.example.demo.adapter.in.web.jwt.JwtProviderHttpServletRequest;
import com.example.demo.adapter.web.dto.RatingDTO;
import com.example.demo.domain.DefaultRateLimitDomainService;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.UserRateLimitService;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import io.jsonwebtoken.Claims;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class HttpServletRequestRatingProcessor implements RepresentationModelProcessor<EntityModel<RatingDTO>> {

    private final HttpServletRequest request;
    private final UserRateLimitService rateLimitService;

    public HttpServletRequestRatingProcessor(RateLimitRepository rateLimitRepository, HttpServletRequest request) {
        this.rateLimitService = new DefaultRateLimitDomainService(rateLimitRepository);
        this.request = request;
    }


    @Override
    public EntityModel<RatingDTO> process(EntityModel<RatingDTO> model) {
        RatingDTO rating = model.getContent();
        if (rating == null) return model;
        if (request.getHeader("Authorization") == null) return model;

        Claims jwt = JwtProviderHttpServletRequest.decodeJwt(request).getBody();
        if (isUserRateLimited(rating, jwt)) return model;

        Link linkToRate = null;
        try {
            linkToRate = Affordances.of(Link.of("self"))
                    .afford(HttpMethod.POST)
                    .withInputMediaType(MediaType.APPLICATION_JSON)
                    .withInput(AddRatingDTORequest.class)
                    .withTarget(linkTo(methodOn(RatingsController.class).saveRating(null, null)).withRel("rate-politician"))
                    .withName("rate-politician")
                    .toLink();
        } catch (UserRateLimitedOnPoliticianException | InterruptedException e) {
            e.printStackTrace(); // LOG THIS INTO A FILE
        }
        return model.add(linkToRate);
    }

    private boolean isUserRateLimited(RatingDTO rating, Claims jwt) {
        return !rateLimitService.isUserNotRateLimited(new AccountNumber(jwt.getId()), PoliticianNumber.of(rating.getPolitician().getId()));
    }

}
