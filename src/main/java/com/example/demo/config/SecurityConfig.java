package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
import org.springframework.web.client.RestTemplate;

import com.example.demo.filter.AddPoliticianFilter;
import com.example.demo.filter.RefreshJwtFilter;
import com.example.demo.oauth2.CustomOauth2AuthorizationRequestsRepository;
import com.example.demo.oauth2.CustomOauth2AuthorizedClientsRepository;
import com.example.demo.oauth2.FacebookOauth2UserInfoUtility;

@EnableWebSecurity
@Configuration
@Profile(value = { "production" })
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private RestTemplate template;
	
	@Value("${facebook.clientSecret}")
	private String OAUTH2_SECRET;

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
			.oauth2Login()
				.disable()
			.oauth2Client()
				.authorizationCodeGrant()
				.authorizationRequestRepository(this.authorizationRequestsRepo())
				.and()
				.authorizedClientRepository(this.authorizedClientRepo())
			.and()
				.addFilterBefore(new AddPoliticianFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new RefreshJwtFilter(), UsernamePasswordAuthenticationFilter.class);
				
	}
	
	@Bean
	public ClientRegistrationRepository clientRepo() {
		return new InMemoryClientRegistrationRepository(this.facebookClientRegistration());
	}
	
	public OAuth2AuthorizedClientRepository authorizedClientRepo() {
		return new CustomOauth2AuthorizedClientsRepository(this.facebookClientRegistration(),
				this.facebookUserInfoEndpointUtility());
	}
	
	@Bean
	public FacebookOauth2UserInfoUtility facebookUserInfoEndpointUtility() {
		return new FacebookOauth2UserInfoUtility(template);
	} 
	
	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestsRepo() {
		return new CustomOauth2AuthorizationRequestsRepository();
	}
	
	private ClientRegistration facebookClientRegistration() {
        return ClientRegistration.withRegistrationId("facebook")
            .clientId("761966061136844")
            .clientSecret(OAUTH2_SECRET)
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
