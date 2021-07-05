package com.example.demo.annotationDiscoverer;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class StringArrayAnnotationMethodMappingDiscoverer extends AnnotationMethodMappingDiscoverer{

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
		String[] annotationValue = super.getAnnotationValueOnMethod(annotationClassType, String[].class); 
		
		if (annotationValue.length == 0) {
			return null;
		}
		
		return Arrays.deepToString(annotationValue).replaceAll(regex, "");
	}
	
	public <T extends Annotation> String getAnnotationValueOnClass(Class<T> annotationClassType, String regex,
			String annotationValueName) {
		String[] annotationValue = super.getAnnotationValueOnClass(annotationClassType, String[].class, annotationValueName); 
		
		if (annotationValue.length == 0) {
			return null;
		}
		
		return Arrays.deepToString(annotationValue).replaceAll(regex, "");
	}

}
