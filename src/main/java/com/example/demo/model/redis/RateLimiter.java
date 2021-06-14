package com.example.demo.model.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "rate_limit")
public class RateLimiter {

	@Id
	private	String id;
	private String politicianNumber;
	@Indexed
	private String accountNumber;
	@TimeToLive
	private Long expiration;
	
	public Long getExpiration() {
		return expiration;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPoliticianNumber() {
		return politicianNumber;
	}
	
	public void setPoliticianNumber(String politicianNumber) {
		this.politicianNumber = politicianNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public RateLimiter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RateLimiter(String id, String politicianNumber, String accountNumber, Long expiration) {
		super();
		this.id = id;
		this.politicianNumber = politicianNumber;
		this.accountNumber = accountNumber;
		this.expiration = expiration;
	}

	@Override
	public String toString() {
		return "RateLimiter [id=" + id + ", politicianNumber=" + politicianNumber + ", accountNumber=" + accountNumber
				+ ", expiration=" + expiration + "]";
	}
	
}
