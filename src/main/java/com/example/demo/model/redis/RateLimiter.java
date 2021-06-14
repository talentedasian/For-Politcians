package com.example.demo.model.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(timeToLive = 604800l)
public class RateLimiter {

	@Id
	private	String id;
	private String politicianNumber;
	private String accountNumber;
	
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

	public RateLimiter(String id, String politicianNumber, String accountNumber) {
		super();
		this.id = id;
		this.politicianNumber = politicianNumber;
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "RateLimiter [id=" + id + ", politicianNumber=" + politicianNumber + ", accountNumber=" + accountNumber
				+ "]";
	}
	
	
}
