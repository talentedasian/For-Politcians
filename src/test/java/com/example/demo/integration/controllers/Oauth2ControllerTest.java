package com.example.demo.integration.controllers;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.controller.Oauth2;
import com.example.demo.jwt.JwtProvider;

@WebMvcTest({Oauth2.class})
public class Oauth2ControllerTest {

	public MockMvc mvc;
	
	@BeforeEach
	public void setup(WebApplicationContext wac) {
		mvc = MockMvcBuilders.webAppContextSetup(wac)
				.alwaysDo(MockMvcResultHandlers.print())
				.build();
	}
	
	@Test
	public void shouldReturnHalForms() throws Exception {
		String jwt = JwtProvider.createJwtWithFixedExpirationDate("test sub", "1111", "test name");
		Cookie cookie = new Cookie("accessJwt", jwt);
		Cookie[] cookies = {cookie};
		
		mvc.perform(get(create("/login/oauth2/code/facebook"))
				.accept(MediaTypes.HAL_FORMS_JSON)
				.cookie(cookies))
			.andExpect(status().isOk())
			.andExpect(jsonPath("jwt",
					equalTo(jwt)));
	}
	
}
