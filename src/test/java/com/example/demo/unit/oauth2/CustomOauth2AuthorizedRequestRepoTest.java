package com.example.demo.unit.oauth2;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dto.FacebookUserInfo;
import com.example.demo.oauth2.CustomOauth2AuthorizedClientsRepository;
import com.example.demo.oauth2.FacebookOauth2UserInfoUtility;

@ExtendWith(SpringExtension.class)
public class CustomOauth2AuthorizedRequestRepoTest {
	
	@MockBean ClientRegistrationRepository oauth2Client;
	@MockBean FacebookOauth2UserInfoUtility facebookClient;
	
	@Mock OAuth2AuthorizedClient authorizedClient;
	@Mock Authentication principal;
	@Mock HttpServletRequest req;
	@Mock HttpServletResponse res;
	
	final FacebookUserInfo userInfo = new FacebookUserInfo("123", "test@gmail.com", "test");
	
	final OAuth2AccessToken accessToken = new OAuth2AccessToken(TokenType.BEARER, "123accessToken", Instant.now(), Instant.now().plusSeconds(32321L));
	
	CustomOauth2AuthorizedClientsRepository customRepo; 
	
	@BeforeEach
	public void setup() {
		customRepo = new CustomOauth2AuthorizedClientsRepository(facebookClient);
	}
	
	@Test
	public void testLogicOfSavingAuthorizedClients() throws URISyntaxException {
		when(facebookClient.fetchUserInfo(any())).thenReturn(userInfo);
		when(authorizedClient.getAccessToken()).thenReturn(accessToken);
		
		customRepo.saveAuthorizedClient(authorizedClient, principal, req, res);
		
		
		verify(facebookClient, times(1)).fetchUserInfo(any());
		verify(res, times(5)).addCookie(any());
	}

}
