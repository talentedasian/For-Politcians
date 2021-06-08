package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<JwtClaims> returnCredentials(HttpServletRequest req, 
			HttpServletResponse res)  {
		Map<String, String> cookieMap = new HashMap<>();
		
		for (Cookie cookies : req.getCookies()) {
			if (cookies.getName().equalsIgnoreCase("accessJwt")) {
				cookieMap.put("jwt", cookies.getValue());
			}
		}
		
		Jws<Claims> jwt = JwtProvider.decodeJwt(cookieMap.get("jwt"));
		JwtClaims jwtResponse = new JwtClaims();
		jwtResponse.setJwt(cookieMap.get("jwt"));
		jwtResponse.setExpiration(jwt.getBody().getExpiration());
		jwtResponse.setId(jwt.getBody().getId());
		jwtResponse.setSubject(jwt.getBody().getSubject());
		jwtResponse.setName(jwt.getBody().get("name", String.class));
		
		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
	}
	
}
