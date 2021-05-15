package com.example.demo.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

import com.example.demo.exceptions.JwtMalformedFormatException;
import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.JwtTamperedExpcetion;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

public class JwtProviderHttpServletRequest {

	public static Jws<Claims> decodeJwt(HttpServletRequest req) {
		System.out.println(req.getRemoteHost() + " tanginamo");
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
		} catch (SignatureException e) {
			throw new JwtTamperedExpcetion("""
					Jwt retrieved had a signature exception or was tampered.
					Server might have restarted without prior knowledge
					""", 
					e); 
		} catch (MalformedJwtException e) {
			throw new JwtTamperedExpcetion(e.getLocalizedMessage());
		}
		
		return jwts;	
	}
	
}
