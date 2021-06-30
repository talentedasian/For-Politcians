package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RatingDTO;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.jwt.JwtClaims;
import com.example.demo.jwt.JwtProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
@RequestMapping("/login/oauth2/code/facebook")
public class Oauth2 {

	@GetMapping
	public ResponseEntity<EntityModel<JwtClaims>> returnCredentials(HttpServletRequest req)  {
		Map<String, String> cookieMap = new HashMap<>();
		
		Arrays.stream(req.getCookies())
			.filter(cookie -> cookie.getName().equalsIgnoreCase("accessJwt"))
			.forEach(jwtCookie -> cookieMap.put("jwt", jwtCookie.getValue()));
		
		Jws<Claims> jwt = JwtProvider.decodeJwt(cookieMap.get("jwt"));
		JwtClaims jwtResponse = new JwtClaims();
		jwtResponse.setJwt(cookieMap.get("jwt"));
		jwtResponse.setExpiration(jwt.getBody().getExpiration());
		jwtResponse.setId(jwt.getBody().getId());
		jwtResponse.setSubject(jwt.getBody().getSubject());
		jwtResponse.setName(jwt.getBody().get("name", String.class));
		
		var affordance = Affordances.of(linkTo(methodOn(PoliticianController.class).allPoliticians())
				.withRel("politicians"))
				.afford(HttpMethod.POST)
				.withOutput(RatingDTO.class)
				.withInput(AddRatingDTORequest.class)
				.withName("rate-politician")
				.withName("rate-politician")
				.build()
				.toLink();
		
		EntityModel<JwtClaims> sirenModel = EntityModel.of(jwtResponse).add(affordance);
				
		
		return new ResponseEntity<EntityModel<JwtClaims>>(sirenModel, HttpStatus.OK);
	}
	
}
