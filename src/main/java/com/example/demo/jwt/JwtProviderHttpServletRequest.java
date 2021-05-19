package com.example.demo.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.example.demo.exceptions.JwtExpiredException;
import com.example.demo.exceptions.JwtMalformedFormatException;
import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.JwtNotFromServerException;
import com.example.demo.exceptions.JwtTamperedExpcetion;
import com.example.demo.exceptions.RefreshTokenException;
import com.example.demo.exceptions.SwaggerJWTException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtProviderHttpServletRequest {

	
	public JwtProviderHttpServletRequest(RestTemplate template) {
		JwtProviderHttpServletRequest.restTemplate = template;
	}

	public static Jws<Claims> decodeJwt(HttpServletRequest req) {
		return decodeJwtUtilMethod(req);
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
			
			
		}  catch (MalformedJwtException e) {
			throw new JwtMalformedFormatException(e.getLocalizedMessage());
		}  catch (ExpiredJwtException e) {
			if (checkIfJwtIsOneHourFresh(e.getClaims().getExpiration())) {
				// RefreshJwtFilter does appropriate refreshing of JsobWeb Tokens
				throw new RefreshTokenException(e.getClaims());
			}
			throw new JwtExpiredException(e.getMessage(), e);
		}  catch (SignatureException e) {
			throw new JwtTamperedExpcetion(e.getLocalizedMessage());
		}
		
		return jwts;
	}
	
	private static boolean checkIfJwtIsOneHourFresh(Date date) {
		LocalDateTime dateTime = LocalDateTime.now().minusHours(1L);
		
		return date.after(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
	}
	
}

