package com.example.demo.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.apiExceptions.ExceptionModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddPoliticianFilter implements Filter{
	
	//Should be changed to an environment variable
	private final String password = "password";

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
		Logger.getLogger("Add Politician Filter")
			.log(java.util.logging.Level.INFO, 
					"IP Address "  + req.getRemoteAddr() + "accessed a resource protected securly");
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr("Authorization Required");
		
		res.setStatus(401);
		res.setContentType("application/json");
		res.getWriter().write(new ObjectMapper().writeValueAsString(exceptionModel));
	}

}
