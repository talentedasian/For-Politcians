package com.example.demo.adapter.in.web;

import com.example.demo.adapter.web.dto.RateLimitJpaEntity;
import com.example.demo.adapter.in.web.dto.RateLimitDto;
import com.example.demo.adapter.in.web.jwt.JwtProviderHttpServletRequest;
import com.example.demo.adapter.out.repository.RateLimitAdapterService;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rate-limit")
public class RateLimitController {
	
	private final RateLimitAdapterService service;

	public RateLimitController(RateLimitAdapterService service) {
		this.service = service;
	}

	@GetMapping("/{politicianNumber}")
	public ResponseEntity<RateLimitDto> findRateLimitOnCurrentUser(@PathVariable String politicianNumber,
																	  HttpServletRequest req) throws UserRateLimitedOnPoliticianException {
		Claims jwt = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		final String accountNumber = jwt.getId();

		RateLimitJpaEntity rateLimitQueried = service.findUsingAccountNumberAndPoliticianNumber(new RateLimitJpaEntity(accountNumber, politicianNumber));

		var selfLink = linkTo(methodOn(RateLimitController.class)
				.findRateLimitOnCurrentUser(rateLimitQueried.getPoliticianNumber(), req))
				.withRel("self");
		var politicianLink = linkTo(methodOn(PoliticianController.class)
				.politicianById(rateLimitQueried.getPoliticianNumber()))
				.withRel("politician");

		var response = RateLimitDto.from(rateLimitQueried.toRateLimit());
		response.add(selfLink, politicianLink);

		return new ResponseEntity<RateLimitDto>(response, HttpStatus.OK);
	}
}
