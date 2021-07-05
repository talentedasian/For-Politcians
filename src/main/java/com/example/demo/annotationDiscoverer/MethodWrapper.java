package com.example.demo.annotationDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

import org.springframework.lang.Nullable;

import io.jsonwebtoken.lang.Assert;

/*
 * Wrapper class for java reflection Methods.
 */
public final class MethodWrapper {

	private final String methodName;
	private final Class<?> classType;
	@Nullable private final Class<?>[] methodParameters;
	
	public String getMethodName() {
		return methodName;
	}

	public Class<?> getClassType() {
		return classType;
	}

	public Class<?>[] getMethodParameters() {
		return methodParameters;
	}

	public MethodWrapper(String methodName, Class<?> clasz, @Nullable Class<?>... methodParameters) {
		super();
		Assert.notNull(methodName, "methodName cannot be null");
		Assert.notNull(clasz, "class cannot be null");
		this.methodName = methodName;
		this.classType = clasz;
		this.methodParameters = methodParameters;
	}

	private Optional<Method> getMethod() {
		try {
			return Optional.of(classType.getDeclaredMethod(methodName, methodParameters));
		} catch (NoSuchMethodException | SecurityException e) {
			return Optional.empty();
		}
	}
	
	public Annotation[] getAnnotations() { 
		Optional<Method> method = getMethod();
		
		if (method.isEmpty()) {
			Annotation[] annotations = {};
			
			return annotations;
		}
		
		return method.get().getDeclaredAnnotations();
	}
	
	public Annotation[] getDeclaredClassAnnotations() {
		return classType.getAnnotations();
	}
	
	public Annotation[] getParameterAnnotations() {
		Optional<Method> method = getMethod();
		
		Annotation[] annotations = new Annotation[10];
		if (method.isEmpty()) {
			return annotations;
		}
		
		int i = 0;
		for (Parameter param : method.get().getParameters()) {
			for (Annotation annotation : param.getDeclaredAnnotations()) {
				annotations[i++] = annotation;
			}
		}
		
		return annotations;
	}
	
	public Parameter[] getParameters() {
		Optional<Method> method = getMethod();
		
		if (method.isEmpty()) {
			return new Parameter[0];
		}
		
		return method.get().getParameters();
	}
	
}