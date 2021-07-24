package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.demo.controller.PoliticianController;
import com.example.demo.exceptionHandling.ExceptionModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Profile("production")
public class AddPoliticianFilterProduction implements Filter{

	@Value("${politician.access.password}")
	private String password;

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
		logAccessDenied(req);
		
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr("Authorization Required");
		
		res.setStatus(401);
		res.setContentType("application/json");
		res.getWriter().write(new ObjectMapper().writeValueAsString(exceptionModel));
	}
	
	private void logAccessDenied(HttpServletRequest req) {
		MDC.put("credentials", req.getHeader("Politician-Access"));
		
		LoggerFactory.getLogger("PoliticianAccess")
		.info("IP Address "  + req.getRemoteAddr() + " accessed a protected resource with wrong credentials");
		
		MDC.clear();
	}

	private boolean isRequestShouldBeHandled(HttpServletRequest req) {
		return getRequestUriToMatch().equals(req.getRequestURI());
	}
	
	private String getRequestUriToMatch() {
		return formUriEndpoint();
	}

	private String formUriEndpoint() {
		return MvcUriComponentsBuilder.fromController(PoliticianController.class).path("/politician")
				.build()
				.getPath()
				.toString();
	}

}
