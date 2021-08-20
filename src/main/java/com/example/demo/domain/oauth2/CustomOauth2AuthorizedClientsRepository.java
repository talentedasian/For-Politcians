package com.example.demo.domain.oauth2;

import com.example.demo.adapter.dto.FacebookUserInfo;
import com.example.demo.adapter.in.web.jwt.JwtJjwtProviderAdapater;
import com.example.demo.domain.userRaterNumber.facebook.FacebookAccountNumberCalculator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomOauth2AuthorizedClientsRepository implements OAuth2AuthorizedClientRepository{
	
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepo;
	
	private final FacebookOauth2UserInfoUtility userInfoEndpointUtil;
	
	@Autowired
	public CustomOauth2AuthorizedClientsRepository(FacebookOauth2UserInfoUtility userInfoEndpointUtil) {
		this.userInfoEndpointUtil = userInfoEndpointUtil; 
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
				(clientRegistrationRepo.findByRegistrationId("facebook"), 
				cookieHolder.get("principalName"), 
				new OAuth2AccessToken(TokenType.BEARER, cookieHolder.get("accessTokenValue"), 
						Instant.ofEpochSecond(Long.valueOf(cookieHolder.get("accessTokenIssuedAt"))),
						Instant.ofEpochSecond(Long.valueOf(cookieHolder.get("accessTokenExpiresAt")))));
		
		return (T) oauth2AuthorizedClient;
	}
	

	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			saveAuthorizedClientsInCookie(authorizedClient, response);
			addJwtCookie(authorizedClient, response);
		} catch (URISyntaxException e) {
			LoggerFactory.getLogger("Oauth2").info("""
					URI exception, consider changing the URI used
					for fetching user information on facebook.""");
		}
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
	
	private void addJwtCookie(OAuth2AuthorizedClient authorizedClient, HttpServletResponse response) throws URISyntaxException{
		FacebookUserInfo userInfo = userInfoEndpointUtil.fetchUserInfo(authorizedClient);

		String accountNumber = FacebookAccountNumberCalculator.with(userInfo.getName(), userInfo.getId()).calculateEntityNumber().getAccountNumber();
		String jwt = JwtJjwtProviderAdapater.createJwtWithFixedExpirationDate(
				userInfo.getEmail(),
				accountNumber,
				userInfo.getName());

		Cookie jwtCookie = new Cookie("accessJwt", jwt);
		jwtCookie.setHttpOnly(true);
		jwtCookie.setPath("/");
		response.addCookie(jwtCookie);
	}
	
}
