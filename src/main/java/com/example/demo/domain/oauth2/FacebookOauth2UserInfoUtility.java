package com.example.demo.domain.oauth2;

import static java.net.URI.create;

import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.ClientAuthorizationRequiredException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.client.RestTemplate;

import com.example.demo.adapter.web.dto.FacebookUserInfo;

public class FacebookOauth2UserInfoUtility {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public FacebookUserInfo fetchUserInfo(OAuth2AuthorizedClient client) throws URISyntaxException {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(client.getAccessToken().getTokenValue());
		
		RequestEntity<Object> requestEntity = new RequestEntity<>(headers,
				HttpMethod.GET, 
				create("https://graph.facebook.com/me?fields=id,name,email"));
		
		ResponseEntity<FacebookUserInfo> responseEntity = restTemplate.exchange(requestEntity, FacebookUserInfo.class);
		//redirect user back to a filter that initiates oauth2 authentication if response is not 2xx
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new ClientAuthorizationRequiredException("facebook");
		}
		
		return responseEntity.getBody();
	}
	

}
