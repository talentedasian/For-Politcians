package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationCodeGrantFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.NullRequestCache;

import com.example.demo.filter.RemoveSessionFilter;
import com.example.demo.oauth2.CustomOauth2AuthorizedClientsRepository;
import com.example.demo.oauth2.Oauth2AuthorizationRequestsRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.sessionFixation()
					.none()
			.and()
			.requestCache()
				.requestCache(this.statelessRequestCache())
			.and()
			.csrf()
				.disable()
			.httpBasic()
				.disable()
			.oauth2Client()
				.authorizationCodeGrant()
				.authorizationRequestRepository(this.authorizationRequestsRepo())
				.and()
				.authorizedClientRepository(this.authorizedClientRepo())
			.and()
				.addFilterAfter(new RemoveSessionFilter(), OAuth2AuthorizationCodeGrantFilter.class);
				
	}
	
	@Bean
	public OAuth2AuthorizedClientService clientService() {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
	}
	
	@Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.facebookClientRegistration());
    }
	
	@Bean
	public OAuth2AuthorizedClientRepository authorizedClientRepo() {
		return new CustomOauth2AuthorizedClientsRepository();
	}
	
	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestsRepo() {
		return new Oauth2AuthorizationRequestsRepository();
	}

	private ClientRegistration facebookClientRegistration() {
        return ClientRegistration.withRegistrationId("facebook")
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
	    }

	private HttpSessionRequestCache statelessRequestCache() {
		HttpSessionRequestCache cache = new HttpSessionRequestCache();
		cache.setCreateSessionAllowed(false);
		return cache;
	}
	
}
