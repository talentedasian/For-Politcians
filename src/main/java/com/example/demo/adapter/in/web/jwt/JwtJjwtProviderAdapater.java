package com.example.demo.adapter.in.web.jwt;

import com.example.demo.domain.JSONWebTokenClaim;
import com.example.demo.domain.JwtDecoder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtJjwtProviderAdapater implements JwtDecoder {

	public static String createJwtWithFixedExpirationDate(String sub, String id, String name) {
		
		String jwts = Jwts.builder()
				.signWith(JwtKeys.getJwtKeyPair().getPrivate())
				.setSubject(sub)
				.claim("name", name)
				.setId(id)
				.setExpiration(new Date(System.currentTimeMillis() + 3600000L))
				.setHeaderParam("login_mechanism", "facebook")
				.compact();
		
		return jwts;
	}
	
	public static String createJwtWithDynamicExpirationDate(String sub, String id, Date expirationDate) {
		
		String jwts = Jwts.builder()
				.signWith(JwtKeys.getJwtKeyPair().getPrivate())
				.setSubject(sub)
				.setId(id)
				.setExpiration(expirationDate)
				.setHeaderParam("login_mechanism", "facebook")
				.compact();
		
		return jwts;
	}
	
	public JSONWebTokenClaim decodeJwt(String jwt) {
		Claims jwts = Jwts.parserBuilder()
				.setSigningKey(JwtKeys.getJwtKeyPair().getPublic())
				.setAllowedClockSkewSeconds(60 * 3)
				.build()
				.parseClaimsJws(jwt)
				.getBody();

		Instant instant = jwts.getExpiration().toInstant();
		var expiration = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Manila"));

		return new JSONWebTokenClaim(jwts.getId(), jwts.getSubject(), jwts.get("name", String.class), expiration);
	}
	
}

