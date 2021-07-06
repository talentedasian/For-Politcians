package com.example.demo;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class BaseSpringHateoasTest {

	@RegisterExtension
	final RestDocumentationExtension restDocumentation = new RestDocumentationExtension("rest-docs");
	
	@Autowired WebApplicationContext applicationContext;
	
	public MockMvc mvc;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mvc = MockMvcBuilders.webAppContextSetup(applicationContext)
				.apply(documentationConfiguration(restDocumentation)
						.operationPreprocessors()
							.withRequestDefaults(prettyPrint())) 
				.alwaysDo(print())
				.build();
	}

}
