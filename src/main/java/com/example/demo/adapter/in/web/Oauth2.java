package com.example.demo.adapter.in.web;

import com.example.demo.adapter.dto.RatingDTO;
import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.in.web.jwt.JwtDto;
import com.example.demo.adapter.in.web.jwt.JwtJjwtProviderAdapater;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Hidden
@RestController
@RequestMapping("/login/oauth2/code/facebook")
public class Oauth2 {

	@SuppressWarnings("deprecation")
	@GetMapping
	public ResponseEntity<RepresentationModel<JwtDto>> returnCredentials(HttpServletRequest req) throws UserRateLimitedOnPoliticianException  {
		Map<String, String> cookieMap = new HashMap<>();
		
		Arrays.stream(req.getCookies())
			.filter(cookie -> cookie.getName().equalsIgnoreCase("accessJwt"))
			.forEach(jwtCookie -> cookieMap.put("jwt", jwtCookie.getValue()));
		
		Claims jwt = JwtJjwtProviderAdapater.decodeJwt(cookieMap.get("jwt")).getBody();

		JwtDto jwtResponse = new JwtDto();
		jwtResponse.setExpiration(convertToLocalDateTimeViaInstant(jwt.getExpiration()));
		jwtResponse.setJwt(cookieMap.get("jwt"));
		jwtResponse.setId(jwt.getId());
		jwtResponse.setSubject(jwt.getSubject());
		jwtResponse.setName(jwt.get("name", String.class));
		
		var affordance = Affordances.of(linkTo(methodOn(PoliticianController.class).allPoliticians())
				.withRel("politicians"))
				.afford(HttpMethod.POST)
				.withOutput(RatingDTO.class)
				.withInput(AddRatingDTORequest.class)
				.withName("rate-politician")
				.withName("rate-politician")
				.withTarget(linkTo(methodOn(RatingsController.class).saveRating(null, null)).withRel("rate"))
				.build()
				.toLink();
		
		jwtResponse.add(affordance);
		jwtResponse.add(linkTo(methodOn(Oauth2.class).returnCredentials(null)).withRel("jwt"));
		
		return new ResponseEntity<RepresentationModel<JwtDto>>(jwtResponse, HttpStatus.OK);
	}

	public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant()
				.atZone(ZoneId.of("GMT+8"))
				.toLocalDateTime();
	}
	
}
