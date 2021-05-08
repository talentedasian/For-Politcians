package com.example.demo.unit.jwt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import com.example.demo.jwt.JwtKeys;
import com.example.demo.jwt.JwtProvider;

import io.jsonwebtoken.Jwts;

public class JwtProviderTest {

	@Test
	public void assertEqualsId() {
		Date dateNow = new Date(System.currentTimeMillis() + 3600000L);
		String actualJwts = Jwts.builder()
				.signWith(JwtKeys.getJwtKeyPair().getPrivate())
				.setSubject("test@gmail.com")
				.setId("test")
				.setExpiration(dateNow)
				.setHeaderParam("login_mechanism", "facebook")
				.compact();
		
		String jwts =JwtProvider.createJwtWithDynamicExpirationDate("test@gmail.com", "test", dateNow);
		
		assertThat(actualJwts, equalTo(jwts));
	}
}
