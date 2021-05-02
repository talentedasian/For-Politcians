package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.csrf()
				.disable()
			.httpBasic()
				.disable()
					.antMatcher("/**")
						.authorizeRequests()
							.anyRequest()
								.permitAll()
									.antMatchers(HttpMethod.POST, "/api/politicians/**")
										.authenticated()
			.and()
			.oauth2Login();
	}

}
