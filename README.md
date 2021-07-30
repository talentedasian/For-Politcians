<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[![Java CI with Maven](https://github.com/talentedasian/For-Politicians/actions/workflows/build.yml/badge.svg?branch=1.x.x.RESTFUL)](https://github.com/talentedasian/For-Politicians/actions/workflows/build.yml)
[![codecov](https://codecov.io/gh/talentedasian/For-Politicians/branch/1.x.x.RESTFUL/graph/badge.svg?token=6VUTJC5D3G)](https://codecov.io/gh/talentedasian/For-Politicians)

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project


A backend made just for politicians in my country to rate their performance by the public. This is to let the public or the citizens critic and rate politicians based on how satisfied they are.

Here's why:
* Politics in the Philippines is becoming hot these past few months because of social media. 
* A lot of fake news have been spreading. 
  1. Credit Grabbing 
  2. Demonizing other politicians
  3. Undermining works of other politicians

This project aims to let the public decide whether this politician did good in their job.

[//]: <>  (A list of commonly used resources that I find helpful are listed in the acknowledgements.)

### Built With

This section should list any major frameworks that you built your project using. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.
* [Spring](https://spring.io/projects/spring-framework)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Security](https://spring.io/projects/spring-security)
* [Spring Data Jpa](https://spring.io/projects/spring-data-jpa)
* [Spring Hateoas](https://spring.io/projects/spring-hateoas)
* [Junit5](https://junit.org/junit5/)



<!-- GETTING STARTED -->
## Getting Started
1. This project uses jdk 16 and maven as the build tool.
2. Install a database of your choice(mine is postgresql).
3. Populate the spring datasource properties.
    ```java
    spring.datasource.url=jdbc:postgresql://localhost:5432/politics
    spring.datasource.username=politics
    spring.datasource.password=politics
    ```
5. Add an `Oauth2Provider` of your own and populate it in a properties file. Mine includes a local-development profile specific property for Oauth2 in my own machine. Not adding any Oauth2 properties would ignore all Oauth2 specific configuration in the `SecurityConfig` class which would break the app.
      ```
        spring.security.oauth2.client.registration.facebook.clientId=697702354184763
        spring.security.oauth2.client.registration.facebook.clientSecret=${OAUTH2_CLIENT_SECRET}
      ```  
Or do it programatically:  
  ```java
  private ClientRegistration facebookClientRegistration() {
        return ClientRegistration.withRegistrationId("facebook")
            .clientId("id")
            .clientSecret("secret")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("{baseUrl}/login/oauth2/code/facebook")
            .authorizationUri("https://www.facebook.com/dialog/oauth")
            .tokenUri("https://graph.facebook.com/v10.0/oauth/access_token")
            .userInfoUri("https://graph.facebook.com/me")
            .userNameAttributeName("id,email")
            .clientName("Facebook")
            .build();
	    }
  ```
6. Add a Security Configuration Class
  ```java
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
				.addFilterBefore(new AddPoliticianFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new RefreshJwtFilter(), UsernamePasswordAuthenticationFilter.class);
				
	}
		
	public OAuth2AuthorizedClientRepository authorizedClientRepo() {
		return new CustomOauth2AuthorizedClientsRepository(this.facebookUserInfoEndpointUtility());
	}
	
	@Bean
	public ClientRegistrationRepository registration() {
		return new InMemoryClientRegistrationRepository(this.facebookClientRegistration());
	}
	
	public FacebookOauth2UserInfoUtility facebookUserInfoEndpointUtility() {
		return new FacebookOauth2UserInfoUtility();
	} 
	
	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestsRepo() {
		return new CustomOauth2AuthorizationRequestsRepository();
	}
	
	public ClientRegistration facebookClientRegistration() {
        return ClientRegistration.withRegistrationId("facebook")
            .clientId(client_id)
            .clientSecret(client_secret)
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
  ```

<!-- ROADMAP -->
## Roadmap
* Add Identifiers for Politicians e.g. Presidential Politician/Senatorial Politician 
X Add Rate Limiting functionality for adding ratings to politicians so users can't abuse their favorite or most hated politician.
X Use Test Containers instead of manually adding a postgresql service on github actions.
X Add Account Numbers for raters
* Add documentation



<!-- CONTRIBUTING -->
## Contributing


1. Fork the Project
2. Append `testcontainers.reuse.enabled=true` to your testcontainer properties which is usually located in `/home/$USER/.testcontainers.properties`
3. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
4. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
5. Push to the Branch (`git push origin feature/AmazingFeature`)
6. Open a Pull Request


<!-- CONTACT -->
## Contact

Discord - [asianmalaysian vietnamese#1514]

