package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/oauth2/code/facebook")
public class Oauth2 {

	@GetMapping
	public void returnCredentials(HttpServletRequest request, HttpServletResponse response) throws IOException {
		new DefaultRedirectStrategy().sendRedirect(request, response, "http://localhost:8080/api/politicians/all");
	}
	
}
