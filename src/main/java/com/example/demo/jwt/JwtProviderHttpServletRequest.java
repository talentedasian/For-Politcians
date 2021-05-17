package com.example.demo.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

import com.example.demo.exceptions.JwtExpiredException;
import com.example.demo.exceptions.JwtMalformedFormatException;
import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.JwtTamperedExpcetion;
import com.example.demo.exceptions.SwaggerJWTException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
			jwts = Jwts.parserBuilder()
					.setSigningKey(JwtKeys.getJwtKeyPair().getPublic())
					.setAllowedClockSkewSeconds(60 * 3)
					.build()
					.parseClaimsJws(req.getHeader("Authorization").substring(7));
			
			Assert.state(req.getHeader("Authorization") != null, 
					"Jwt used cannot be used in a swagger environment");
		} catch (Exception e) {
			// TODO: handle exception
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
		} catch (ExpiredJwtException e) {
			throw new JwtExpiredException(e.getMessage(), e);
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
			
			Assert.state((!(jwts.getBody().getExpiration() == null) || !(jwts.getBody().getSubject().equalsIgnoreCase("test@gmail.com"))), 
					"Jwt not valid");
		}  catch (MalformedJwtException e) {
			throw new JwtTamperedExpcetion(e.getLocalizedMessage());
		}  catch (ExpiredJwtException e) {
			if (checkIfJwtIsOneHourFresh(e.getClaims().getExpiration())) {
				String jwt = JwtProvider.createJwtWithFixedExpirationDate(e.getClaims().getSubject(), e.getClaims().getId());
				
				return Jwts.parserBuilder()
						.setSigningKey(JwtKeys.getJwtKeyPair().getPublic())
						.setAllowedClockSkewSeconds(60 * 3)
						.build()
						.parseClaimsJws(jwt);
			}
			
			throw new JwtExpiredException(e.getMessage(), e);
		}  catch (IllegalStateException e) {
			throw new SwaggerJWTException("JWT for swagger not valid");
		}
		
		return jwts;
	}
	
	private static boolean checkIfJwtIsOneHourFresh(Date date) {
		LocalDateTime dateTime = LocalDateTime.now().minusHours(1L);
		
		return date.after(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
	}
	
}

