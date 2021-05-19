package com.example.demo.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.jwt.JwtProvider;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SpringDoc {

	private final String jwt = JwtProvider.createJwtWithNoExpirationDate("test@gmail.com", "test");

	@Bean
	public OpenAPI openApi() {
		Info info = new Info();
		info.setVersion("1.0.0");
		info.setDescription("Use this jwt `" + jwt + "` in using the \"add-rating\" endpoint from the RatingController");
		
		OAuthFlow oauthFlow = new OAuthFlow();
		oauthFlow.authorizationUrl("http://localhost:8080/oauth2/authorization/facebook");
		oauthFlow.setTokenUrl("https://graph.facebook.com/v10.0/oauth/access_token");
		oauthFlow.setRefreshUrl("http://localhost:8080/swagger-ui/oauth2-redirect.html");
		
		SecurityScheme securityScheme = new SecurityScheme();
		securityScheme.type(Type.OAUTH2);
		securityScheme.scheme("bearer");
		securityScheme.in(In.HEADER);
		securityScheme.bearerFormat("jwt");
		securityScheme.flows(new OAuthFlows().authorizationCode(oauthFlow));
		
		var openApi = new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("add-rating", new SecurityScheme()
								.type(Type.HTTP).scheme("bearer").bearerFormat("JWT"))
						.addSecuritySchemes("oauth2", securityScheme))
				.addSecurityItem(new SecurityRequirement().addList("oauth2"));
		
		openApi.setInfo(info);
		
		return openApi;
	}
}
