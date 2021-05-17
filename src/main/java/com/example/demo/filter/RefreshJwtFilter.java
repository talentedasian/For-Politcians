package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.jwt.JwtProvider;
import com.example.demo.jwt.JwtProviderHttpServletRequest;

import io.jsonwebtoken.Claims;

public class RefreshJwtFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		Claims refreshedJwtClaims = JwtProviderHttpServletRequest.decodeJwt(req).getBody();
		
		String jwt = JwtProvider.createJwtWithFixedExpirationDate(refreshedJwtClaims.getSubject(), refreshedJwtClaims.getId());
		
		Cookie jwtCookie = new Cookie("accessJwt", jwt);
		jwtCookie.setHttpOnly(true);
		jwtCookie.setPath("/");
		
		res.addCookie(jwtCookie);
		
		chain.doFilter(request, response);
		
	}

}
