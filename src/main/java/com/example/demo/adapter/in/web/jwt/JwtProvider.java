package com.example.demo.adapter.in.web.jwt;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtProvider {

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
	
	public static String createJwtWithNoExpirationDate(String sub, String id) {
		
		String jwts = Jwts.builder()
				.signWith(JwtKeys.getJwtKeyPair().getPrivate())
				.setSubject(sub)
				.setId(id)
				.setHeaderParam("login_mechanism", "facebook")
				.compact();
		
		return jwts;
	}
	
	public static Jws<Claims> decodeJwt(String jwt) {
		Jws<Claims> jwts = Jwts.parserBuilder()
				.setSigningKey(JwtKeys.getJwtKeyPair().getPublic())
				.setAllowedClockSkewSeconds(60 * 3)
				.build()
				.parseClaimsJws(jwt);

		return jwts;
	}
	
}

