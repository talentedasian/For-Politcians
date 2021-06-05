package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.session.SessionManagementFilter;

import com.example.demo.filter.AddPoliticianFilter;
import com.example.demo.filter.RefreshJwtFilter;
import com.example.demo.oauth2.CustomOauth2AuthorizationRequestsRepository;
import com.example.demo.oauth2.CustomOauth2AuthorizedClientsRepository;
import com.example.demo.oauth2.FacebookOauth2UserInfoUtility;

@Configuration
@EnableWebSecurity
public class SecurityConfigProduction extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
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
					.addFilterBefore(new AddPoliticianFilter(), SessionManagementFilter.class)
					.addFilterBefore(new RefreshJwtFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	public OAuth2AuthorizedClientRepository authorizedClientRepo() {
		return new CustomOauth2AuthorizedClientsRepository(this.facebookUserInfoEndpointUtility());
	}
	
	@Bean
	public FacebookOauth2UserInfoUtility facebookUserInfoEndpointUtility() {
		return new FacebookOauth2UserInfoUtility();
	}
	
	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestsRepo() {
		return new CustomOauth2AuthorizationRequestsRepository();
	}
	
	private HttpSessionRequestCache statelessRequestCache() {
		HttpSessionRequestCache cache = new HttpSessionRequestCache();
		cache.setCreateSessionAllowed(false);
		return cache;
	}

}
