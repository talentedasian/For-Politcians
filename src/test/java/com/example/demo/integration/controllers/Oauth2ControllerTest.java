package com.example.demo.integration.controllers;

import static java.net.URI.create;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.jwt.JwtProvider;

@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT, printOnlyOnFailure = false)
public class Oauth2ControllerTest {

	@Autowired public MockMvc mvc;
	
	@Test
	public void shouldReturnHalForms() throws Exception {
		String jwt = JwtProvider.createJwtWithFixedExpirationDate("test sub", "1111", "test name");
		Cookie cookie = new Cookie("accessJwt", jwt);
		Cookie[] cookies = {cookie};
		
		mvc.perform(get(create("/login/oauth2/code/facebook"))
				.accept(MediaTypes.HAL_FORMS_JSON)
				.cookie(cookies))
			.andExpect(status().isOk());
	}
	
}
