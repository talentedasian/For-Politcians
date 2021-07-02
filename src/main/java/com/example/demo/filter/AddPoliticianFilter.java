package com.example.demo.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.annotationDiscoverer.MethodWrapper;
import com.example.demo.annotationDiscoverer.StringArrayAnnotationMethodMappingDiscoverer;
import com.example.demo.controller.PoliticianController;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.exceptionHandling.ExceptionModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Profile({ "localDevelopment,test" })
public class AddPoliticianFilter implements Filter{
	
	private String password = "password";
	private static final String REMOVE_SPECIAL_CHAR = "[\\[\\]]";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (isRequestShouldBeHandled(req)) {
			if (req.getHeader("Politician-Access") != null) {
				if (req.getHeader("Politician-Access").equalsIgnoreCase(password)) {
					//essentially do nothing
				} else {
					handleAddPoliticianAccessDenied(req, res);
					return;					
				}
				
			} else {
				handleAddPoliticianAccessDenied(req, res);
				return;
			}
		}
		
		chain.doFilter(req, res);
	}

	private void handleAddPoliticianAccessDenied(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		LoggerFactory.getLogger("Add Politician Filter")
			.info("IP Address "  + req.getRemoteAddr() + " accessed a protected resource with wrong credentials");
		
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr("Authorization Required");
		
		res.setStatus(401);
		res.setContentType("application/json");
		res.getWriter().write(new ObjectMapper().writeValueAsString(exceptionModel));
	}
	
	private boolean isRequestShouldBeHandled(HttpServletRequest req) {
		return getRequestUriToMatch().equals(req.getRequestURI());
	}
	
	private String getRequestUriToMatch() {
		var method = new MethodWrapper("savePolitician", PoliticianController.class, AddPoliticianDTORequest.class); 
		var discoverer = new StringArrayAnnotationMethodMappingDiscoverer(method, "value");
		
		String httpEndpoint = discoverer.getAnnotationValue(PostMapping.class, REMOVE_SPECIAL_CHAR);
		System.out.println(httpEndpoint + "pota bat ganto");
		/*
		 * Implement a class for extracting annotation values from the future.
		 */
		String[] apiEndpointArray = MergedAnnotation.from(method.getMethod().get().getDeclaringClass().getAnnotation(RequestMapping.class)).getStringArray("value");
		String apiEndpoint = Arrays.deepToString(apiEndpointArray).replaceAll("[\\[\\]]", "");
		System.out.println(apiEndpoint.concat(httpEndpoint) + " potangina");
		
		return apiEndpoint.concat(httpEndpoint);
	}

}
