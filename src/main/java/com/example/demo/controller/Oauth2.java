package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/oauth2/code")
public class Oauth2 {

	@GetMapping
	public RedirectView returnCredentials(@RequestParam String code, @RequestParam String state) {
		System.out.println(code  + " tanginamo");
		return new RedirectView("http://localhost:8080/api/politicians/politicianByName?name=Leni Robredos");
				
	}
	
	@PostMapping
	public RedirectView returnCredentialsPost(@RequestParam String code, @RequestParam String state) {
		System.out.println(code  + " tanginamo");
		return new RedirectView("http://localhost:8080/api/politicians/politicianByName?name=Leni Robredos");
				
	}
}
