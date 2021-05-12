package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.apiExceptions.ExceptionModel;
import com.example.demo.exceptions.JwtMalformedFormatException;
import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.JwtTamperedExpcetion;
import com.example.demo.jwt.JwtProviderHttpServletRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;

public class ProtectedResourceOuath2JwtFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (req.getServletPath().equalsIgnoreCase("/api/ratings/add-rating")) {
			if (req.getHeader("Authorization").isEmpty()) {
				handleJwtNotFoundException(req, res);
				return;
			} else {
				try {
					Jws<Claims> jwt = JwtProviderHttpServletRequest.decodeJwt(req);
				} catch (JwtTamperedExpcetion e) {
					handleJwtSignatureException(req, res);
					return;
				} catch (JwtMalformedFormatException e) {
					handleJwtMalformedException(req, res, e);
					return;
				} catch (IllegalStateException e) {
					handleJwtIllegalStateException(req, res, e);
					return;
				}
			}
		}
		
		chain.doFilter(req, res);
		
	}
	
	private void handleJwtNotFoundException(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(new JwtNotFoundException("No Jwt found on add rating endpoint").getLocalizedMessage());
		exceptionModel.setOptional("The endpoint you are accessing requires a valid Json Web Token to be sent in the "
				+ "Authorization Header");
		
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionModel));
	}
	
	private void handleJwtMalformedException(HttpServletRequest request, HttpServletResponse response, JwtMalformedFormatException e) throws JsonProcessingException, IOException {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(e.getLocalizedMessage());
		
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionModel));
	}
	
	private void handleJwtSignatureException(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(new JwtTamperedExpcetion("Jwt token was tampered. Server might have restarted without prior knowledge").getMessage());
		
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionModel));
	}
	
	private void handleJwtIllegalStateException(HttpServletRequest request, HttpServletResponse response, IllegalStateException e) throws JsonProcessingException, IOException {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(e.getMessage());
		
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionModel));
	}
	
}

