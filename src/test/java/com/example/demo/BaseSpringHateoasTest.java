package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class BaseSpringHateoasTest {

	@RegisterExtension
	final RestDocumentationExtension restDocumentation = new RestDocumentationExtension("rest-docs");
	
	WebTestClient webTestClient;
	@Autowired WebApplicationContext applicationContext;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.webTestClient = MockMvcWebTestClient.bindToApplicationContext(applicationContext)
				.configureClient()
				.filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation)) 
				.build();
	}

}
