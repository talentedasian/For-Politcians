package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.exceptions.RefreshTokenException;
import com.example.demo.adapter.in.web.jwt.JwtUtils;

public class RefreshJwtFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		
		try {
			chain.doFilter(request, response);			
		} catch (Exception ex) {
			if (ex.getCause() instanceof RefreshTokenException) {
				RefreshTokenException e = (RefreshTokenException) ex.getCause();
				String jwt = JwtUtils.fixedExpirationDate(e.getClaims().getSubject(),
						e.getClaims().getId(), e.getClaims().get("fullName", String.class));
								
				Cookie jwtCookie = new Cookie("accessJwt", jwt);
				jwtCookie.setHttpOnly(true);
				jwtCookie.setPath("/");
				
				res.addCookie(jwtCookie);
			}
		}
		
	}

}
