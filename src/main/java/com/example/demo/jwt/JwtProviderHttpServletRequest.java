package com.example.demo.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;

import com.example.demo.exceptions.JwtNotFoundException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtProviderHttpServletRequest {

	public static Jws<Claims> decodeJwt(HttpServletRequest req) {
		Jws<Claims> jwts = null;
		Assert.state(req.getHeader("Authorization") != null, 
				"No jwt found on authorization header");
		Assert.state(req.getHeader("Authorization").startsWith("Bearer "), 
				"Authorization Header must start with \"Bearer\"");
		try {
			jwts = Jwts.parserBuilder()
					.setSigningKey(JwtKeys.getJwtKeyPair().getPublic())
					.setAllowedClockSkewSeconds(60 * 3)
					.build()
					.parseClaimsJws(req.getHeader("Authorization").substring(7));

		} catch (IllegalStateException e) {
			throw new JwtNotFoundException("""
					No jwt found on authorization Header or Authorization or 
					Authorization Header did not start with "Bearer"
					""", 
					e);
		}
		
		return jwts;	
	}
	
}
