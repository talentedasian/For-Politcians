package com.example.demo.annotationDiscoverer;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

public final class StringArrayAnnotationMethodMappingDiscoverer extends AnnotationMethodMappingDiscoverer{

	public StringArrayAnnotationMethodMappingDiscoverer(MethodWrapper method, String annotationValueName) {
		super(method, annotationValueName);
	}

	/*
	 * Pass in a regex to filter out unwanted characters.
	 * For example you want the String array to be exactly
	 * "/value", the getAnnotationValueOnMethod of the super
	 * class might return unwanted characters if the return 
	 * type is a String array. 
	 */
	public <T extends Annotation> String getAnnotationValue(Class<T> annotationClassType, String regex) {
		Optional<String[]> annotationValue = super.getAnnotationValueOnMethod(annotationClassType, String[].class); 
		
		if (annotationValue.isEmpty()) {
			return "";
		}
		
		return Arrays.deepToString(annotationValue.get()).replaceAll(regex, "");
	}

}
