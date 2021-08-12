package com.example.demo.adapter.in.web;

import com.example.demo.adapter.dto.RateLimitJpaDto;
import com.example.demo.adapter.in.web.jwt.JwtProviderHttpServletRequest;
import com.example.demo.adapter.out.repository.RateLimitAdapterService;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.domain.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
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
	public ResponseEntity<RateLimitJpaDto> findRateLimitOnCurrentUser(@PathVariable String politicianNumber,
                                                                      HttpServletRequest req) {
		Claims jwt = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		
		AbstractUserRaterNumber accountNumberCalculator = FacebookUserRaterNumberImplementor.with(jwt.get("name", String.class), jwt.getId());
		String accountNumber = accountNumberCalculator.calculateEntityNumber().getAccountNumber();
		
		RateLimitJpaDto rateLimitQueried = service.findUsingAccountNumberAndPoliticianNumber(RateLimitJpaDto.of(new RateLimit(accountNumber, politicianNumber)));

		var selfLink = linkTo(methodOn(RateLimitController.class)
				.findRateLimitOnCurrentUser(rateLimitQueried.getPoliticianNumber(), req))
				.withRel("self");
		
		rateLimitQueried.add(selfLink);
		
		return new ResponseEntity<RateLimitJpaDto>(rateLimitQueried, HttpStatus.OK);
	}
}
