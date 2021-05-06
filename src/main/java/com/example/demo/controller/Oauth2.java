package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("login/oauth2/code/facebook")
public class Oauth2 {

	@GetMapping
	public void returnCredentials(HttpServletRequest request, HttpServletResponse response) throws IOException {
		new DefaultRedirectStrategy().sendRedirect(request, response, "http://localhost:8080/api/politicians/politicianByName?name=Leni Robredo");
	}
	
	@PostMapping
	public RedirectView returnCredentialsPost(@RequestParam String code, @RequestParam String state) {
		return new RedirectView("http://localhost:8080/api/politicians/politicianByName?name=Leni Robredos");
				
	}
	
}
