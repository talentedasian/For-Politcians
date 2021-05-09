package com.example.demo.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.JwtTamperedExpcetion;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

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
		} catch (SignatureException e) {
			throw new JwtTamperedExpcetion("""
					Jwt retrieved had a signature exception or was tampered.
					Server might have restarted without prior knowledge
					""", 
					e); 
		}
		
		return jwts;	
	}
	
}
