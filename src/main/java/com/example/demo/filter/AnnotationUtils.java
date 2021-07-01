package com.example.demo.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;

public class AnnotationUtils {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Optional <T> getAnnotation(Class<?> klass, Class annotationKlassType, 
			String annotationName, String annotationValueName, Class<T> annotationValueType,
			Class<?>... parametersType) {
		Method method = BeanUtils.findMethod(klass ,annotationName, parametersType);
		if (method == null) {
			return Optional.empty();
		}
		
		MergedAnnotation mergedAnnotation = MergedAnnotations.from(method).get(annotationKlassType);
		
		Optional<T> anValue = mergedAnnotation.getValue(annotationValueName, annotationValueType);
		if (anValue.isPresent()) {
			if (anValue.get() instanceof String[] arr) {
				return (Optional<T>) Optional.of(Arrays.deepToString(arr).replaceAll("[\\[\\]]", ""));
			}
		}
		
		System.out.println(anValue.get() + " tangina");
		return anValue;
	}
	
	@Test
	public void testAnnotation() throws NoSuchMethodException, SecurityException {
		Optional<String[]> annotationValue = getAnnotation(PoliticianController.class, PostMapping.class, 
				"savePolitician", "value", String[].class, AddPoliticianDTORequest.class);
		
		assertEquals("/politician", annotationValue.get());
	}
}
