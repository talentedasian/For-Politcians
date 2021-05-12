package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.client.RestTemplate;

import com.example.demo.filter.AddPoliticianFilter;
import com.example.demo.filter.ProtectedResourceOuath2JwtFilter;
import com.example.demo.oauth2.CustomOauth2AuthorizationRequestsRepository;
import com.example.demo.oauth2.CustomOauth2AuthorizedClientsRepository;

@Configuration
@Profile("production")
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private RestTemplate template;
	@Autowired
	private ClientRegistrationRepository clientRepo;

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
				.addFilterBefore(new ProtectedResourceOuath2JwtFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new AddPoliticianFilter(), ProtectedResourceOuath2JwtFilter.class);
				
	}
		
	public OAuth2AuthorizedClientRepository authorizedClientRepo() {
		return new CustomOauth2AuthorizedClientsRepository(template, clientRepo.findByRegistrationId("facebook"));
	}
	
	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestsRepo() {
		return new CustomOauth2AuthorizationRequestsRepository();
	}
	
	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
	
	private HttpSessionRequestCache statelessRequestCache() {
		HttpSessionRequestCache cache = new HttpSessionRequestCache();
		cache.setCreateSessionAllowed(false);
		return cache;
	}
	
}
