package com.example.demo.mockMvcUtils;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.util.UriTemplate;

import com.example.demo.annotationDiscoverer.MethodWrapper;
import com.example.demo.annotationDiscoverer.RequestMappingAnnotationDiscoverer;

public class MockMvcPathUtils {
	
	public static String createPath(String methodName, Class<?> clazz,
			HttpMethod httpMethod, @Nullable Map<String, Object> uriVariables, 
			Class<?>... parameterClasses) {
		return formUri(httpMethod, methodName, clazz, uriVariables, parameterClasses);
	}
	
	static String formUri(HttpMethod httpMethod, String methodName, Class<?> clazz,
			@Nullable Map<String, Object> uriVariables, 
			Class<?>... parameterClasses) {
		var method = new MethodWrapper(methodName, clazz, parameterClasses);
		var discoverer = new RequestMappingAnnotationDiscoverer(method, "value");
	
		String uri = formEndpointFromHttpMethod(httpMethod, discoverer);
		
		if (uriVariables == null) {
			return uri;
		}
		
		return new UriTemplate(uri).expand(uriVariables).toString();
	}

	private static String formEndpointFromHttpMethod(HttpMethod httpMethod, 
			RequestMappingAnnotationDiscoverer discoverer) {
		String baseUri = discoverer.getAnnotationRequestMappingPathValueOnClass();
		
		String uri = null;
		
		switch (httpMethod) {
		case GET -> {uri = baseUri.concat(discoverer.getAnnotationGetMappingPathValue());}
		case POST -> {uri = baseUri.concat(discoverer.getAnnotationPostMappingPathValue());}
		case PUT -> {uri = baseUri.concat(discoverer.getAnnotationPutMappingPathValue());}
		case DELETE -> {uri = baseUri.concat(discoverer.getAnnotationDeleteMappingPathValue());}
		default -> throw new IllegalArgumentException("Unexpected value: " + httpMethod);
		}
		
		return uri;
	}
	
	

}
