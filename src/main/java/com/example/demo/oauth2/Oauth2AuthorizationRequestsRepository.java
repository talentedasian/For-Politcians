package com.example.demo.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

public class Oauth2AuthorizationRequestsRepository implements OAuth2AuthorizedClientService{

	@Override
	public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
			String principalName) {
		System.out.println("tanginamo");
		return null;
	}

	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
		// TODO Auto-generated method stub
		System.out.println("tanginamo ulit");
	}

	@Override
	public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
		// TODO Auto-generated method stub
		System.out.println("tanginamo ulit ulit");
	}

	

}
