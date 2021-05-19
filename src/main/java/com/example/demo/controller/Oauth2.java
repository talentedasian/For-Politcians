package com.example.demo.controller;

import java.io.IOException;
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

@RestController
@RequestMapping("/login/oauth2/code/facebook")
public class Oauth2 {

	@GetMapping
	public ResponseEntity<Map<String, String>> returnCredentials(HttpServletRequest req, 
			HttpServletResponse res) throws IOException {
		Map<String, String> map = new HashMap<>();
		for (Cookie cookies : req.getCookies()) {
			if (cookies.getName().equalsIgnoreCase("accessJwt")) {
				map.put("jwt", cookies.getValue());
			}
		}
		map.put("token", map.get("jwt"));
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
}
