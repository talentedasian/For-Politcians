package com.example.demo.unit.annotationMappingDiscoverer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.demo.annotationDiscoverer.MethodWrapper;
import com.example.demo.annotationDiscoverer.RequestMappingAnnotationDiscoverer;
import com.example.demo.controller.PoliticianController;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;

public class RequestMappingDiscovererTest {

	@Test
	public void testPostMappingDiscoverer() {
		var method = new MethodWrapper("savePolitician", PoliticianController.class, AddPoliticianDTORequest.class);
		var discoverer = new RequestMappingAnnotationDiscoverer(method, "value");
		
		assertEquals("/politician", discoverer.getAnnotationPostMappingPathValue());
	}
	
	@Test
	public void testGetMappingDiscoverer() {
		var method = new MethodWrapper("politicianByName", PoliticianController.class, String.class, String.class);
		var discoverer = new RequestMappingAnnotationDiscoverer(method, "value");
		
		assertEquals("/politician", discoverer.getAnnotationGetMappingPathValue());
	}
	
}
