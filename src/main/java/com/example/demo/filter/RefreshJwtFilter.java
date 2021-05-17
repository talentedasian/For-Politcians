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

import com.example.demo.exceptions.RefreshTokenException;
import com.example.demo.jwt.JwtProvider;

public class RefreshJwtFilter implements Filter{

	@SuppressWarnings("preview")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			chain.doFilter(request, response);			
		} catch (Exception ex) {
			if (ex.getCause() instanceof RefreshTokenException e) {
				String jwt = JwtProvider.createJwtWithFixedExpirationDate(e.getClaims().getSubject(),
						e.getClaims().getId());
								
				Cookie jwtCookie = new Cookie("accessJwt", jwt);
				jwtCookie.setHttpOnly(true);
				jwtCookie.setPath("/");
				
				res.addCookie(jwtCookie);
			}
		}
		
	}
	
	

}
