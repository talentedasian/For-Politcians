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
		String actualJwts = Jwts.builder()
				.signWith(JwtKeys.getJwtKeyPair().getPrivate())
				.setSubject("test@gmail.com")
				.setId("test")
				.setExpiration(new Date(System.currentTimeMillis() + 3600000L))
				.setHeaderParam("login_mechanism", "facebook")
				.compact();
		
		String jwts =JwtProvider.createJwt("test@gmail.com", "id");
		
		assertThat(actualJwts, equalTo(jwts));
	}
}
