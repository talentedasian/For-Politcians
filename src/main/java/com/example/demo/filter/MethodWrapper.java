package com.example.demo.filter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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

	public MethodWrapper(String methodName, Class<?> classType, @Nullable Class<?>... methodParameters) {
		super();
		Assert.notNull(methodName, "methodName cannot be null");
		Assert.notNull(classType, "classType cannot be null");
		this.methodName = methodName;
		this.classType = classType;
		this.methodParameters = methodParameters;
	}

	public Optional<Method> getMethod() {
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
	
}

