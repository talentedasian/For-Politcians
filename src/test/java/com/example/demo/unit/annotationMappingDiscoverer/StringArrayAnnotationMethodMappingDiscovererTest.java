package com.example.demo.unit.annotationMappingDiscoverer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.annotationDiscoverer.MethodWrapper;
import com.example.demo.annotationDiscoverer.StringArrayAnnotationMethodMappingDiscoverer;
import com.example.demo.controller.PoliticianController;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;

public class StringArrayAnnotationMethodMappingDiscovererTest {

	@Test
	public void assertLogicOfDiscoverer() {
		var method = new MethodWrapper("savePolitician", PoliticianController.class, AddPoliticianDTORequest.class);
		var discoverer = new StringArrayAnnotationMethodMappingDiscoverer(method, "value");
		
		assertEquals("/politician", discoverer.getAnnotationValue(PostMapping.class, "[\\[\\]]"));
	}
	
	@Test
	public void assertLogicOfDiscovererShouldReturnSpecialCharacters() {
		var method = new MethodWrapper("savePolitician", PoliticianController.class, AddPoliticianDTORequest.class);
		var discoverer = new StringArrayAnnotationMethodMappingDiscoverer(method, "value");
		
		assertEquals("[/]", discoverer.getAnnotationValue(PostMapping.class, "[a-zA-Z]"));
	}
	
}
