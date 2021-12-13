package com.example.demo.adapter.in.web.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import com.example.demo.JwtExpiration;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class JwtUtils {

	public static String fixedExpirationDate(String sub, String id, String name) {
		
		String jwts = Jwts.builder()
				.signWith(JwtKeys.getJwtKeyPair().getPrivate())
				.setSubject(sub)
				.claim("fullName", name)
				.setId(id)
				.setExpiration(new Date(System.currentTimeMillis() + 3600000L))
				.setHeaderParam("login_mechanism", "facebook")
				.compact();
		
		return jwts;
	}

	public static String dynamicExpirationDate(String sub, String id, LocalDateTime expiration) {
		Date date = Date.from(expiration.toInstant(ZoneOffset.of("+8")));

		String jwts = Jwts.builder()
				.signWith(JwtKeys.getJwtKeyPair().getPrivate())
				.setSubject(sub)
				.setId(id)
				.setExpiration(date)
				.setHeaderParam("login_mechanism", "facebook")
				.compact();

		return jwts;
	}

	public static String dynamicExpirationDate(String sub, String id, JwtExpiration expiration) {
		Date date = Date.from(expiration.created());

		String jwts = Jwts.builder()
				.signWith(JwtKeys.getJwtKeyPair().getPrivate())
				.setSubject(sub)
				.setId(id)
				.setExpiration(date)
				.setHeaderParam("login_mechanism", "facebook")
				.compact();

		return jwts;
	}

	public static Jws<Claims> decodeJwt(String jwt) {
		return Jwts.parserBuilder()
				.setSigningKey(JwtKeys.getJwtKeyPair().getPublic())
				.setAllowedClockSkewSeconds(60 * 3)
				.build()
				.parseClaimsJws(jwt);
	}
	
}

