package com.example.demo.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class Oauth2CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println(request.getServletPath());
//		System.out.println(request.getParameterMap());
//		System.out.println(request.getParameter("code"));
//		OAuth2User user = (OAuth2User) authentication;
//		System.out.println(user.getAttributes());
		
	}

	

}
