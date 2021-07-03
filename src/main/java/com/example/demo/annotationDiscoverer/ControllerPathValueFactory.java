package com.example.demo.annotationDiscoverer;

import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

public class ControllerPathValueFactory {

	private final RequestMappingAnnotationDiscoverer discoverer;


	public ControllerPathValueFactory(RequestMappingAnnotationDiscoverer discoverer) {
		super(); 
		Assert.notNull(discoverer,  "discoverer cannot be null"); 
		this.discoverer = discoverer;
	 }
	
	public String formUriEndpoint(HttpMethod httpMethod) {
		String baseUri = discoverer.getAnnotationRequestMappingPathValueOnClass();
		
		switch (httpMethod) {
		case GET -> { return baseUri.concat(discoverer.getAnnotationGetMappingPathValue()); }
		case DELETE -> { return baseUri.concat(discoverer.getAnnotationDeleteMappingPathValue()); }  
		case POST -> { return baseUri.concat(discoverer.getAnnotationPostMappingPathValue()); }
		case PUT -> { return baseUri.concat(discoverer.getAnnotationPutMappingPathValue()); }
		default -> throw new IllegalArgumentException("Unsupported HTTP Method " + httpMethod.toString());
		}
	}

}
