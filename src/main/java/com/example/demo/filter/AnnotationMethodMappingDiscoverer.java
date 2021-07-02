package com.example.demo.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;

/*
 * Discoverer for annotations present in methods
 */
public class AnnotationMethodMappingDiscoverer{
	
	private final MethodWrapper method;
	private final String annotationName;
	private final MergedAnnotations mergedAnnotations;

	public String getAnnotationName() {
		return annotationName;
	}
	
	public AnnotationMethodMappingDiscoverer(MethodWrapper method, String annotationName) {
		super();
		Assert.notNull(method, "method should not be null");
		Assert.notNull(annotationName, "annotationName should not be null");
		
		this.method = method;
		this.annotationName = annotationName;
		this.mergedAnnotations = MergedAnnotations.from(this.method.getAnnotations());
	}

	/*
	 * Get the value of an annotation that is present on a method using a 
	 * method wrapper class.
	 */
	public <T extends Annotation, Z> Optional<Z> getAnnotationValueOnMethod(Class<T> annotationClassType, 
			Class<Z> annotationValueType) {
		MergedAnnotation<T> annotation = mergedAnnotations.get(annotationClassType);
		if (annotation.isDirectlyPresent()) {
			return Optional.of(annotation.getValue(annotationName, annotationValueType).get());
		}
		
		return Optional.empty();
		
	}

}
