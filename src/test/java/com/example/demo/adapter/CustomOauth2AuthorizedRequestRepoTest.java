package com.example.demo.adapter;

import com.example.demo.adapter.in.web.jwt.JwtJjwtProviderAdapater;
import com.example.demo.adapter.web.dto.FacebookUserInfo;
import com.example.demo.domain.oauth2.CustomOauth2AuthorizedClientsRepository;
import com.example.demo.domain.oauth2.FacebookOauth2UserInfoUtility;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.net.URISyntaxException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = { ClientRegistration.class })
public class CustomOauth2AuthorizedRequestRepoTest {
	
	@MockBean ClientRegistrationRepository oauth2Client;
	@MockBean FacebookOauth2UserInfoUtility facebookClient;
	
	@Autowired ClientRegistration reg;
	final OAuth2AccessToken accessToken = new OAuth2AccessToken(TokenType.BEARER, "123accessToken", Instant.now(), Instant.now().plusSeconds(32321L));
	
	final String EMAIL = "test@gmail.com";
	final String SUBJECT = EMAIL;
	final String ID = "123";
	final String NAME = "test";
	
	Authentication principal = null;
	MockHttpServletRequest req = new MockHttpServletRequest();
	MockHttpServletResponse res = new MockHttpServletResponse();
	
	final FacebookUserInfo userInfo = new FacebookUserInfo(ID, EMAIL, NAME);
	
	
	CustomOauth2AuthorizedClientsRepository customRepo; 
	
	@BeforeEach
	public void setup() {
		customRepo = new CustomOauth2AuthorizedClientsRepository(facebookClient);
	}
	
	@Test
	public void shouldReturnJwtAsCookieWhenSuccessfulOauthProcess() throws URISyntaxException {
		String jwt = JwtJjwtProviderAdapater.createJwtWithFixedExpirationDate(SUBJECT, ID, NAME);
		
		when(facebookClient.fetchUserInfo(any())).thenReturn(userInfo);
		OAuth2AuthorizedClient authorizedClient = new OAuth2AuthorizedClient(reg, "anonymous", accessToken);
		
		customRepo.saveAuthorizedClient(authorizedClient, principal, req, res);

		Claims decodedJwt = JwtJjwtProviderAdapater.decodeJwt(jwt).getBody();
		Claims jwtFromSuccessfulOauthProcess = JwtJjwtProviderAdapater.decodeJwt(res.getCookie("accessJwt").getValue()).getBody();

		assertThat(decodedJwt.getSubject())
				.isEqualTo(jwtFromSuccessfulOauthProcess.getSubject());
		assertThat(decodedJwt.get("name"))
				.isEqualTo(jwtFromSuccessfulOauthProcess.get("name"));
		assertThat(decodedJwt.getExpiration())
				.isCloseTo(jwtFromSuccessfulOauthProcess.getExpiration(), 2000);
	}

}
