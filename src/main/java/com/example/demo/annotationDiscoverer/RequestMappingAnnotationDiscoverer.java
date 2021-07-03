package com.example.demo.annotationDiscoverer;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public final class RequestMappingAnnotationDiscoverer extends StringArrayAnnotationMethodMappingDiscoverer{

	public RequestMappingAnnotationDiscoverer(MethodWrapper method, String annotationValueName) {
		super(method, annotationValueName);
	}
	
	public String getAnnotationGetMappingPathValue() {
		return getAnnotationValue(GetMapping.class, "[\\[\\]]");
	}
	
	public String getAnnotationDeleteMappingPathValue() {
		return getAnnotationValue(DeleteMapping.class, "[\\[\\]]");
	}
	
	public String getAnnotationPutMappingPathValue() {
		return getAnnotationValue(PutMapping.class, "[\\[\\]]");
	}
	
	public String getAnnotationPostMappingPathValue() {
		return getAnnotationValue(PostMapping.class, "[\\[\\]]");
	}
	
	public String getAnnotationRequestMappingPathValueOnClass() {
		return getAnnotationValueOnClass(RequestMapping.class, "[\\[\\]]", "value");
	}

}
