package com.example.demo.domain.oauth2;

import com.example.demo.adapter.in.service.RatingServiceAdapter;
import com.example.demo.adapter.in.web.jwt.JwtProviderHttpServletRequest;
import com.example.demo.domain.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/facebook/delete")
public class FacebookDataDeletion {
	
	private final RatingServiceAdapter service;

	public FacebookDataDeletion(RatingServiceAdapter service) {
		super();
		this.service = service;
	}

	@DeleteMapping
	public ResponseEntity<?> deleteFacebookRelatedUserData(HttpServletRequest req) {
		Claims jwt = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		
		String accNumber = FacebookUserRaterNumberImplementor.with(jwt.get("fullName", String.class), jwt.getId()).calculateEntityNumber().getAccountNumber();
		
		service.deleteUsingAccountNumber(accNumber);
		
		return ResponseEntity.noContent().build();
	}

}
