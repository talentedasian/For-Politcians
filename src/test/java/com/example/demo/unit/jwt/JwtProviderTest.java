package com.example.demo.unit.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.example.demo.jwt.JwtKeys;
import com.example.demo.jwt.JwtProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtProviderTest {

	@Test
	public void assertEqualsEncodedJwt() {
		Date dateNow = new Date(System.currentTimeMillis() + 3600000L);
		
		String actualJwts = Jwts.builder()
				.signWith(JwtKeys.getJwtKeyPair().getPrivate())
				.setSubject("test@gmail.com")
				.setId("test")
				.setExpiration(dateNow)
				.setHeaderParam("login_mechanism", "facebook")
				.compact();
		
		String jwts =JwtProvider.createJwtWithDynamicExpirationDate("test@gmail.com", "test", dateNow);
		
		assertEquals(actualJwts, jwts);
	}
	
	@Test
	public void assertEqualsDecodedJwt() {
		Date dateNow = new Date(System.currentTimeMillis() + 3600000L);
		String encodedJwts = JwtProvider.createJwtWithDynamicExpirationDate("test@gmail.com", "test", dateNow);
		
		Jws<Claims> decodedJwts = JwtProvider.decodeJwt(encodedJwts);
		
		Claims actualJwts = decodedJwts.getBody();
		
		assertEquals("test@gmail.com", actualJwts.getSubject());
		assertEquals("test", actualJwts.getId());
		assertEquals(dateNow.toString(), actualJwts.getExpiration().toString());
		assertEquals(true, actualJwts.getExpiration().before(dateNow));
	}
}
