package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RateLimitDTO;
import com.example.demo.dtomapper.RateLimitDtoMapper;
import com.example.demo.exceptions.RateLimitNotFoundException;
import com.example.demo.jwt.JwtProviderHttpServletRequest;
import com.example.demo.model.entities.RateLimit;
import com.example.demo.model.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
import com.example.demo.service.RateLimitingService;

import io.jsonwebtoken.Claims;

@RestController("/rate-limit")
public class RateLimitController {
	
	private final RateLimitingService service;
	
	public RateLimitController(RateLimitingService service) {
		super();
		this.service = service;
	}


	@GetMapping("/{politicianNumber}")
	public ResponseEntity<?> findRateLimitOnCurrentUser(@PathVariable String politicianNumber,
			HttpServletRequest req) {
		Claims jwt = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		
		AbstractUserRaterNumber accountNumberCalculator = FacebookUserRaterNumberImplementor.with(jwt.get("name", String.class), jwt.getId());
		String accountNumber = accountNumberCalculator.calculateEntityNumber().getAccountNumber();
		
		RateLimit rateLimitQueried = service.findRateLimitInPolitician(accountNumber, politicianNumber)
				.orElseThrow(() -> new RateLimitNotFoundException("User is not rate limited"));
		
		RateLimitDTO rateLimit = new RateLimitDtoMapper().mapToDTO(rateLimitQueried);
		var selfLink = linkTo(methodOn(RateLimitController.class)
				.findRateLimitOnCurrentUser(rateLimit.getPoliticianNumber(), req))
				.withRel("self");
		
		rateLimit.add(selfLink);
		
		return new ResponseEntity<>(rateLimit, HttpStatus.OK);
	}
	

}
