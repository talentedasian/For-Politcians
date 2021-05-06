package com.example.demo.oauth2;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;

public class AuthorizedRequestsRepository implements OAuth2AuthorizedClientRepository{

	private static final ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("facebook")
	        .clientId("697702354184763")
	        .clientSecret("88e0d00193984f18f0a21f234091702d")
	        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	        .redirectUri("{baseUrl}/login/oauth2/code/facebook")
	        .authorizationUri("https://www.facebook.com/dialog/oauth")
	        .tokenUri("https://graph.facebook.com/v10.0/oauth/access_token")
	        .userInfoUri("https://graph.facebook.com/me")
	        .userNameAttributeName("id,email")
	        .clientName("Facebook")
	        .build();
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
			Authentication principal, HttpServletRequest request) {
		Map<String, String> cookieRequestHolder = new HashMap<>();
		List<String> parameterLists = List.of("accessTokenValue", "accessTokenExpiresAt","accessTokenIssuedAt", "principalName");
		for (Cookie cookies : request.getCookies()) {
			System.out.println(cookies.getValue());
			if (parameterLists.contains(cookies.getName())) {
				cookieRequestHolder.put(cookies.getName(), cookies.getValue());
			}
		}
		
		OAuth2AuthorizedClient oauth2AuthorizedClient = new OAuth2AuthorizedClient
				(clientRegistration, 
				clientRegistrationId, 
				new OAuth2AccessToken(TokenType.BEARER, cookieRequestHolder.get("accessTokenValue"), 
						Instant.ofEpochSecond(Long.valueOf(cookieRequestHolder.get("accessTokenIssuedAt"))),
						Instant.ofEpochSecond(Long.valueOf(cookieRequestHolder.get("accessTokenExpiresAt")))));
		
		return (T) oauth2AuthorizedClient;
	}
	

	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Cookie accessTokenValueCookie = new Cookie("accessTokenValue", 
				authorizedClient.getAccessToken().getTokenValue());
		accessTokenValueCookie.setHttpOnly(true);
		Cookie accessTokenExpiresAtCookie = new Cookie("accessTokenExpiresAt", 
				String.valueOf(authorizedClient.getAccessToken().getExpiresAt().getEpochSecond()));
		accessTokenExpiresAtCookie.setHttpOnly(true);
		Cookie accessTokenIssuedAtCookie = new Cookie("accessTokenIssuedAt", 
				String.valueOf(authorizedClient.getAccessToken().getIssuedAt().getEpochSecond()));
		accessTokenIssuedAtCookie.setHttpOnly(true);
		Cookie principalNameCookie = new Cookie("principalName",
				authorizedClient.getPrincipalName());
		principalNameCookie.setHttpOnly(true);
		
		response.addCookie(accessTokenValueCookie);
		response.addCookie(accessTokenExpiresAtCookie);
		response.addCookie(accessTokenIssuedAtCookie);
		response.addCookie(principalNameCookie);		
	}

	@Override
	public void removeAuthorizedClient(String clientRegistrationId, Authentication principal,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Not really removing anything");
		
	} 

}
