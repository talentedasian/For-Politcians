package com.example.demo.integration.jwt;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.exceptions.JwtExpiredException;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.jwt.JwtProviderHttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class RefreshingJwtTest {

	@Mock
	public HttpServletRequest req;
	
	@Test
	public void shouldReturnFreshJwtWithOneHourExpiration() throws Exception {
		LocalDateTime dateTime = LocalDateTime.now().minusHours(1L);
		Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		String jwt = JwtProvider.createJwtWithDynamicExpirationDate("test@gmail.com", "test", date);
		when(req.getHeader("Referer")).thenReturn("ds");
		when(req.getHeader("Authorization")).thenReturn("Bearer " + jwt);
		
		assertThrows(JwtExpiredException.class,
				() -> JwtProviderHttpServletRequest.decodeJwt(req));
	}
	
}
