package com.example.demo.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

import com.example.demo.exceptions.JwtMalformedFormatException;
import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.JwtTamperedExpcetion;
import com.example.demo.exceptions.SwaggerJWTUsedNotInSwagger;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

public class JwtProviderHttpServletRequest {

	public static Jws<Claims> decodeJwt(HttpServletRequest req) {
		if (req.getHeader("Referer").contains("swagger")) {
			return decodeJwtUtilMethodSwagger(req);
		}
		return decodeJwtUtilMethod(req);
	}
	
	private static Jws<Claims> decodeJwtUtilMethodSwagger(HttpServletRequest req) {
		Jws<Claims> jwts = null;
		try {
			Assert.state(req.getHeader("Authorization") != null, 
					"No jwt found on authorization header");			
		} catch (IllegalStateException e) {
			throw new JwtNotFoundException(e.getMessage(), e);
		} 
		
		try {
			Assert.state(req.getHeader("Authorization").startsWith("Bearer "), 
					"Authorization Header must start with Bearer");			
		} catch (IllegalStateException e) {
			throw new JwtMalformedFormatException(e.getMessage(), e);
		}
		
		try {
			jwts = Jwts.parserBuilder()
					.setSigningKey(JwtKeys.getJwtKeyPair().getPublic())
					.setAllowedClockSkewSeconds(60 * 3)
					.build()
					.parseClaimsJws(req.getHeader("Authorization").substring(7));
		}  catch (MalformedJwtException e) {
			throw new JwtTamperedExpcetion(e.getLocalizedMessage());
		}
		
		return jwts;
	}

	private static Jws<Claims> decodeJwtUtilMethod(HttpServletRequest req) {
		Jws<Claims> jwts = null;
		try {
			
		} catch (IllegalStateException e) {
			throw new SwaggerJWTUsedNotInSwagger("JWT for swagger is used in a non swagger environment");
		}
		
		try {
			Assert.state(req.getHeader("Authorization") != null, 
					"No jwt found on authorization header");			
		} catch (IllegalStateException e) {
			throw new JwtNotFoundException(e.getMessage(), e);
		} 
		
		try {
			Assert.state(req.getHeader("Authorization").startsWith("Bearer "), 
					"Authorization Header must start with Bearer");			
		} catch (IllegalStateException e) {
			throw new JwtMalformedFormatException(e.getMessage(), e);
		}
		
		try {
			jwts = Jwts.parserBuilder()
					.setSigningKey(JwtKeys.getJwtKeyPair().getPublic())
					.setAllowedClockSkewSeconds(60 * 3)
					.build()
					.parseClaimsJws(req.getHeader("Authorization").substring(7));
		}  catch (MalformedJwtException e) {
			throw new JwtTamperedExpcetion(e.getLocalizedMessage());
		}
		
		return jwts;
	}
	
}
