package com.example.demo.adapter.web;

import com.example.demo.adapter.in.web.jwt.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.togetherjava.tjbot.commands.utility.JwtExpiration;

import java.time.LocalDateTime;

import static com.example.demo.adapter.in.web.jwt.JwtUtils.decodeJwt;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JwtUtilsTest {

	final String SUBJECT = "test@gmail.com";
	final String ID = "test";

	@Test
	public void shouldThrowExpiredException() throws Exception{
		LocalDateTime cannotBeRefreshedExpiredJwt = LocalDateTime.now().minusDays(7);
		String id = ACC_NUMBER().accountNumber();
		String jwt = JwtUtils.dynamicExpirationDate("test@gmail.com", id, cannotBeRefreshedExpiredJwt);

		ThrowableAssert.ThrowingCallable decodingOfJwtThrowsExpiredException = () -> decodeJwt(jwt);

		assertThatThrownBy(decodingOfJwtThrowsExpiredException)
				.isInstanceOf(ExpiredJwtException.class);
	}

	@Test
	public void jwtCreated2HoursBeforeBasedOnGmt8ShouldThrowExceptionWhenDecoding() throws Exception{
		JwtExpiration twoHoursBehind = JwtExpiration.ofBehind(120);
		String id = ACC_NUMBER().accountNumber();
		String jwt = JwtUtils.dynamicExpirationDate("test@gmail.com", id, twoHoursBehind);

		assertThatThrownBy(() -> decodeJwt(jwt))
				.isInstanceOf(ExpiredJwtException.class);
	}

}
