package com.example.demo.annotationDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.Assert;

/*
 * Discoverer for annotations present in methods
 */
public class AnnotationMethodMappingDiscoverer{
	
	private final MethodWrapper method;
	private final String annotationName;
	private final MergedAnnotations mergedAnnotations;
	private final MergedAnnotations classMergedAnnotations;

	public String getAnnotationName() {
		return annotationName;
	}
	
	MethodWrapper getMethod() {
		return method;
	}

	MergedAnnotations getMergedAnnotations() {
		return mergedAnnotations;
	}

	MergedAnnotations getClassMergedAnnotations() {
		return classMergedAnnotations;
	}

	public AnnotationMethodMappingDiscoverer(MethodWrapper method, String annotationValueName) {
		super();
		Assert.notNull(method, "method should not be null");
		Assert.notNull(annotationValueName, "annotationName should not be null");
		
		this.method = method;
		this.annotationName = annotationValueName;
		this.mergedAnnotations = MergedAnnotations.from(this.method.getAnnotations());
		this.classMergedAnnotations = MergedAnnotations.from(this.method.getDeclaredClassAnnotations());
	}

	/*
	 * Get the value of an annotation that is present on a method using a 
	 * method wrapper class. If the value is a string array and has unwanted 
	 * characters, use the StringArrayAnnotationMethodMappingDiscoverer class 
	 * instead and pass your own regex to extract out the unwanted characters. 
	 */
	public <T extends Annotation, Z> Z getAnnotationValueOnMethod(Class<T> annotationClassType, 
			Class<Z> annotationValueType) {
		MergedAnnotation<T> annotation = mergedAnnotations.get(annotationClassType);
		if (annotation.isDirectlyPresent()) {
			return annotation.getValue(annotationName, annotationValueType).get();
		}
		
		return null;
	}
	
	/*
	 * 
	 */
	public <T extends Annotation, Z> Z getAnnotationValueOnClass(Class<T> annotationInClassType, 
			Class<Z> annotationInClassValueType, String annotationValueName) {
		MergedAnnotation<T> annotation = classMergedAnnotations.get(annotationInClassType);
		if (annotation.isDirectlyPresent()) {
			return annotation.getValue(annotationValueName, annotationInClassValueType).get();
		}
		
		return null;
	} 
	
	public Parameter[] getMethodParameters() {
		Parameter[] params = this.method.getParameters();
		if (params.length == 0) {
			return null;
		}
		
		return params;
	}

}
