package com.example.demo.integration.jwt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.util.NestedServletException;

import com.example.demo.exceptions.RefreshTokenException;
import com.example.demo.filter.RefreshJwtFilter;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.jwt.JwtProviderHttpServletRequest;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { RefreshJwtFilter.class })
public class RefreshingJwtTest {

	@Mock
	public HttpServletRequest req;
	@Mock
	public HttpServletResponse res;
	
	@Test
	public void shouldReturnFreshJwtWithOneHourExpiration() throws Exception {
		LocalDateTime dateTime = LocalDateTime.now();
		Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		String jwt = JwtProvider.createJwtWithDynamicExpirationDate("nanay@gmail.com", "nanay", date);
		
		when(req.getHeader("Authorization")).thenReturn("Bearer " + jwt);
		RefreshJwtFilter filter = new RefreshJwtFilter();
		FilterChain filterChain = Mockito.mock(FilterChain.class);
		doThrow(new NestedServletException("nice",
				new RefreshTokenException(JwtProviderHttpServletRequest.decodeJwt(req).getBody()))).when(filterChain).doFilter(req, res);
		
		filter.doFilter(req, res, filterChain);
		verify(res, times(1)).addCookie(any(Cookie.class));
		
	}
	
}
