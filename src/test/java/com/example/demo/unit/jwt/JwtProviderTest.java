package com.example.demo.unit.jwt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
		
		assertThat(actualJwts, 
				equalTo(jwts));
	}
	
	@Test
	public void assertEqualsDecodedJwt() {
		Date dateNow = new Date(System.currentTimeMillis() + 3600000L);
		String encodedJwts = JwtProvider.createJwtWithDynamicExpirationDate("test@gmail.com", "test", dateNow);
		
		Jws<Claims> decodedJwts = JwtProvider.decodeJwt(encodedJwts);
		
		Claims actualJwts = decodedJwts.getBody();
		
		assertThat("test@gmail.com", 
				equalTo(actualJwts.getSubject()));
		assertThat("test", 
				equalTo(actualJwts.getId()));
		assertThat(dateNow.toString(), 
				equalTo(actualJwts.getExpiration().toString()));
		assertThat(true, 
				equalTo(actualJwts.getExpiration().before(dateNow)));
	}
}
