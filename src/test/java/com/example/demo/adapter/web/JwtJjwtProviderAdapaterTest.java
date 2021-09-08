package com.example.demo.adapter.web;

import com.example.demo.adapter.in.web.jwt.JwtJjwtProviderAdapater;
import com.example.demo.adapter.in.web.jwt.JwtKeys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtJjwtProviderAdapaterTest {

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
		
		String expected = JwtJjwtProviderAdapater.createJwtWithDynamicExpirationDate(SUBJECT, ID, dateNow);
		
		assertEquals(expected, actualJwts);
	}
	
	@Test
	public void assertEqualsDecodedJwt() {
		Date dateNow = new Date(System.currentTimeMillis() + 3600000L);
		String encodedJwts = JwtJjwtProviderAdapater.createJwtWithDynamicExpirationDate(SUBJECT, ID, dateNow);
		
		Jws<Claims> decodedJwts = JwtJjwtProviderAdapater.decodeJwt(encodedJwts);
		
		Claims actualJwts = decodedJwts.getBody();
		
		assertEquals(SUBJECT, actualJwts.getSubject());
		assertEquals(ID, actualJwts.getId());
		assertEquals(dateNow.toString(), actualJwts.getExpiration().toString());
		assertEquals(true, actualJwts.getExpiration().before(dateNow));
	}
	
}
