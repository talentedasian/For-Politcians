package com.example.demo.oauth2;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.ClientAuthorizationRequiredException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.FacebookUserInfo;

public class FacebookOauth2UserInfoUtility {
	
	private final RestTemplate restTemplate;
	
	public FacebookOauth2UserInfoUtility(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public FacebookUserInfo fetchUserInfo(OAuth2AuthorizedClient client) throws URISyntaxException {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(client.getAccessToken().getTokenValue());
		RequestEntity<Object> requestEntity = new RequestEntity<>(headers,
				HttpMethod.GET, new URI("https://graph.facebook.com/me?fields=id,email,name"));
		ResponseEntity<FacebookUserInfo> responseEntity = restTemplate.exchange(requestEntity, FacebookUserInfo.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			//This causes a redirect to a spring security filter that handles oauth2 authentications
			throw new ClientAuthorizationRequiredException("facebook");
		}
		
		return responseEntity.getBody();
	}
	

}
