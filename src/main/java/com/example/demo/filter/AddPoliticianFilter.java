package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import com.example.demo.apiExceptions.ExceptionModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Profile({ "localDevelopment,test" })
public class AddPoliticianFilter implements Filter{
	
	private String password = "password";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if (req.getRequestURI().equalsIgnoreCase("/api/politicians/add-politician")) {
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

}
