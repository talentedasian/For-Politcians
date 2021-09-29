package com.example.demo.adapter.in.web;

import com.example.demo.adapter.in.web.dto.RateLimitDto;
import com.example.demo.adapter.in.web.jwt.JwtProviderHttpServletRequest;
import com.example.demo.adapter.out.repository.RateLimitAdapterService;
import com.example.demo.adapter.web.dto.RateLimitJpaEntity;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import io.jsonwebtoken.Claims;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rate-limit")
public class RateLimitController {
	
	private final RateLimitAdapterService service;
	private final RateLimitAssembler assembler;

	public RateLimitController(RateLimitAdapterService service, RateLimitAssembler assembler) {
		this.service = service;
		this.assembler = assembler;
	}

	@GetMapping("/{politicianNumber}")
	public ResponseEntity<EntityModel<RateLimitDto>> findRateLimitOnCurrentUser(@PathVariable String politicianNumber,
																				HttpServletRequest req) throws UserRateLimitedOnPoliticianException {
		Claims jwt = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		final String accountNumber = jwt.getId();

		RateLimitJpaEntity rateLimitQueried = service.findUsingAccountNumberAndPoliticianNumber(new RateLimitJpaEntity(accountNumber, politicianNumber));

		var rateLimitDto = RateLimitDto.from(rateLimitQueried.toRateLimit());
		EntityModel<RateLimitDto> response = assembler.toModel(rateLimitDto);

		return new ResponseEntity<EntityModel<RateLimitDto>>(response, HttpStatus.OK);
	}

}
