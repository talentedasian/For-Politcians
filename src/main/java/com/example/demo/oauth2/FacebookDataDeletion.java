package com.example.demo.oauth2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwt.JwtProviderHttpServletRequest;
import com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
import com.example.demo.service.RatingService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/facebook/delete")
public class FacebookDataDeletion {
	
	private final RatingService service;

	public FacebookDataDeletion(RatingService service) {
		super();
		this.service = service;
	}

	@DeleteMapping
	public ResponseEntity<?> deleteFacebookRelatedUserData(HttpServletRequest req) {
		Claims jwt = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		
		String accNumber = FacebookUserRaterNumberImplementor.with(jwt.get("name", String.class), jwt.getId()).calculateEntityNumber().getAccountNumber();
		
		service.deleteByAccountNumber(accNumber);
		
		return ResponseEntity.noContent().build();
	}

}
