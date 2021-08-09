package com.example.demo.adapter.in.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.adapter.in.service.RateLimitingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.adapter.dto.RateLimitDTO;
import com.example.demo.dtomapper.RateLimitDtoMapper;
import com.example.demo.exceptions.RateLimitNotFoundException;
import com.example.demo.adapter.in.web.jwt.JwtProviderHttpServletRequest;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.domain.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/rate-limit")
public class RateLimitController {
	
	private final RateLimitingService service;
	
	public RateLimitController(RateLimitingService service) {
		this.service = service;
	}


	@GetMapping("/{politicianNumber}")
	public ResponseEntity<RateLimitDTO> findRateLimitOnCurrentUser(@PathVariable String politicianNumber,
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
		
		return new ResponseEntity<RateLimitDTO>(rateLimit, HttpStatus.OK);
	}
}
