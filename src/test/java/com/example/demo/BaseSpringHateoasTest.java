package com.example.demo;

import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.filter.AddPoliticianFilter;
import com.example.demo.filter.RefreshJwtFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseSpringHateoasTest extends BaseClassTestsThatUsesDatabase {

	@RegisterExtension
	final RestDocumentationExtension restDocumentation = new RestDocumentationExtension("rest-docs");
	
	public MockMvc mvc;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation, WebApplicationContext wac) {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilters(new RefreshJwtFilter(), new AddPoliticianFilter())
				.apply(documentationConfiguration(restDocumentation)
						.operationPreprocessors()
							.withRequestDefaults(prettyPrint())) 
				.alwaysDo(print())
				.build();
	}

}
