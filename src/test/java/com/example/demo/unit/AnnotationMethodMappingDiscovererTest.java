package com.example.demo.unit;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.filter.AnnotationMethodMappingDiscoverer;
import com.example.demo.filter.MethodWrapper;

public class AnnotationMethodMappingDiscovererTest {
	
	@Test
	public void testAnnotation() {
		MethodWrapper method = new MethodWrapper("savePolitician", PoliticianController.class, AddPoliticianDTORequest.class);
		var discoverer = new AnnotationMethodMappingDiscoverer(method, "value");
		
		assertThat(Arrays.deepToString(discoverer.getAnnotationValueOnMethod(PostMapping.class, String[].class).get()), 
				containsStringIgnoringCase("/politician"));
	}

}
