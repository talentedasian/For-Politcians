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

public class RemoveSessionFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		req.getSession().invalidate();
		Cookie invalidateSessionCookie = new Cookie("JSESSIONID", "");
		invalidateSessionCookie.setHttpOnly(true);
		invalidateSessionCookie.setPath("/");
		res.addCookie(invalidateSessionCookie);
		
		chain.doFilter(request, response);
	}

}

