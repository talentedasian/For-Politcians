package com.example.demo.integration.controllers;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;

import com.example.demo.BaseSpringHateoasTest;
import com.example.demo.jwt.JwtProvider;

public class Oauth2ControllerTest extends BaseSpringHateoasTest{	
	
	@Test
	public void shouldReturnHalForms() throws Exception {
		String jwt = JwtProvider.createJwtWithFixedExpirationDate("test sub", "1111", "test name");
		Cookie cookie = new Cookie("accessJwt", jwt);
		Cookie[] cookies = {cookie};
		
		mvc.perform(get(create("/login/oauth2/code/facebook"))
				.accept(MediaTypes.HAL_FORMS_JSON)
				.cookie(cookies))
			.andExpect(content().contentType(HAL_FORMS_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("jwt",
					equalTo(jwt)))
			.andDo(document("oauth", links(halLinks(),
					linkWithRel("politicians").description("Retrieve all politician"),
					linkWithRel("jwt").description("Jwt status and description"))));
	}
	
}
