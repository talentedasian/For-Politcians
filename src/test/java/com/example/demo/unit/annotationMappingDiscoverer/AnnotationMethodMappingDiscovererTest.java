package com.example.demo.unit.annotationMappingDiscoverer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.annotationDiscoverer.AnnotationMethodMappingDiscoverer;
import com.example.demo.annotationDiscoverer.MethodWrapper;
import com.example.demo.controller.PoliticianController;
import com.example.demo.service.PoliticiansService;

public class AnnotationMethodMappingDiscovererTest {
	
	@Test
	public void assertLogicOfDiscoverer() {
		MethodWrapper method = new MethodWrapper("findPoliticianByNumber", PoliticiansService.class, String.class);
		var discoverer = new AnnotationMethodMappingDiscoverer(method, "readOnly");
		
		assertEquals(true, 
				discoverer.getAnnotationValueOnMethod(Transactional.class, Boolean.class).get());
	}

}
