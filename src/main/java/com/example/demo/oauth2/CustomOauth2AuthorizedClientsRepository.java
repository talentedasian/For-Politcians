package com.example.demo.oauth2;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.ClientAuthorizationRequiredException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.FacebookUserInfo;
import com.example.demo.jwt.JwtProvider;

public class CustomOauth2AuthorizedClientsRepository implements OAuth2AuthorizedClientRepository{
	
	private RestTemplate restTemplate;
	private ClientRegistration clientRegistrationFacebook;
	
	public CustomOauth2AuthorizedClientsRepository(RestTemplate restTemplate, ClientRegistration clientRegistrationFacebook) {
		this.restTemplate = restTemplate;
		this.clientRegistrationFacebook = clientRegistrationFacebook; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
			Authentication principal, HttpServletRequest request) {
		Map<String, String> cookieHolder = new HashMap<>();
		List<String> parameterLists = List.of("accessTokenValue", "accessTokenExpiresAt","accessTokenIssuedAt", "principalName");
		for (Cookie cookies : request.getCookies()) {
			if (parameterLists.contains(cookies.getName())) {
				cookieHolder.put(cookies.getName(), cookies.getValue());
			}
		}
		
		OAuth2AuthorizedClient oauth2AuthorizedClient = new OAuth2AuthorizedClient
				(clientRegistrationFacebook, 
				cookieHolder.get("principalName"), 
				new OAuth2AccessToken(TokenType.BEARER, cookieHolder.get("accessTokenValue"), 
						Instant.ofEpochSecond(Long.valueOf(cookieHolder.get("accessTokenIssuedAt"))),
						Instant.ofEpochSecond(Long.valueOf(cookieHolder.get("accessTokenExpiresAt")))));
		
		return (T) oauth2AuthorizedClient;
	}
	

	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal,
			HttpServletRequest request, HttpServletResponse response) {
		saveAuthorizedClientsInCookie(authorizedClient, response);
		addJwtCookie(authorizedClient, response);
	}

	@Override
	public void removeAuthorizedClient(String clientRegistrationId, Authentication principal,
			HttpServletRequest request, HttpServletResponse response) {
		this.loadAuthorizedClient(clientRegistrationId, principal, request);
	} 

	private void saveAuthorizedClientsInCookie(OAuth2AuthorizedClient authorizedClient, HttpServletResponse response) {
		Cookie accessTokenValueCookie = new Cookie("accessTokenValue", 
				authorizedClient.getAccessToken().getTokenValue());
		accessTokenValueCookie.setHttpOnly(true);
		accessTokenValueCookie.setPath("/");
		Cookie accessTokenExpiresAtCookie = new Cookie("accessTokenExpiresAt", 
				String.valueOf(authorizedClient.getAccessToken().getExpiresAt().getEpochSecond()));
		accessTokenExpiresAtCookie.setHttpOnly(true);
		accessTokenExpiresAtCookie.setPath("/");
		Cookie accessTokenIssuedAtCookie = new Cookie("accessTokenIssuedAt", 
				String.valueOf(authorizedClient.getAccessToken().getIssuedAt().getEpochSecond()));
		accessTokenIssuedAtCookie.setHttpOnly(true);
		accessTokenIssuedAtCookie.setPath("/");
		Cookie principalNameCookie = new Cookie("principalName",
				authorizedClient.getPrincipalName());
		principalNameCookie.setPath("/");
		principalNameCookie.setHttpOnly(true);
		
		response.addCookie(accessTokenValueCookie);
		response.addCookie(accessTokenExpiresAtCookie);
		response.addCookie(accessTokenIssuedAtCookie);
		response.addCookie(principalNameCookie);
	}
	
	private void addJwtCookie(OAuth2AuthorizedClient authorizedClient, HttpServletResponse response) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
			RequestEntity<Object> requestEntity = new RequestEntity<>(headers,
					HttpMethod.GET, new URI("https://graph.facebook.com/me?fields=id,email,name"));
			ResponseEntity<FacebookUserInfo> responseEntity = restTemplate.exchange(requestEntity, FacebookUserInfo.class);
			
			if (!responseEntity.getStatusCode().is2xxSuccessful()) {
				//This causes a redirect to a spring security filter that handles oauth2 authentications
				throw new ClientAuthorizationRequiredException("facebook");
				}
			
			String jwt = JwtProvider.createJwtWithFixedExpirationDate(responseEntity.getBody().getEmail(),
					responseEntity.getBody().getName());
							
			Cookie jwtCookie = new Cookie("accessJwt", jwt);
			jwtCookie.setHttpOnly(true);
			jwtCookie.setPath("/");
			
			response.addCookie(jwtCookie);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}
	
}
