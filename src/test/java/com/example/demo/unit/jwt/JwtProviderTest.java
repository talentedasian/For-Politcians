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

	final String SUBJECT = "test@gmail.com";
	final String ID = "test";

	@Test
	public void assertEqualsEncodedJwt() {
		Date dateNow = new Date(System.currentTimeMillis() + 3600000L);
		
		String actualJwts = Jwts.builder()
				.signWith(JwtKeys.getJwtKeyPair().getPrivate())
				.setSubject(SUBJECT)
				.setId(ID)
				.setExpiration(dateNow)
				.setHeaderParam("login_mechanism", "facebook")
				.compact();
		
		String expected =JwtProvider.createJwtWithDynamicExpirationDate(SUBJECT, ID, dateNow);
		
		assertEquals(expected, actualJwts);
	}
	
	@Test
	public void assertEqualsDecodedJwt() {
		Date dateNow = new Date(System.currentTimeMillis() + 3600000L);
		String encodedJwts = JwtProvider.createJwtWithDynamicExpirationDate(SUBJECT, ID, dateNow);
		
		Jws<Claims> decodedJwts = JwtProvider.decodeJwt(encodedJwts);
		
		Claims actualJwts = decodedJwts.getBody();
		
		assertEquals(SUBJECT, actualJwts.getSubject());
		assertEquals(ID, actualJwts.getId());
		assertEquals(dateNow.toString(), actualJwts.getExpiration().toString());
		assertEquals(true, actualJwts.getExpiration().before(dateNow));
	}
	
}
