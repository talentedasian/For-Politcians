package com.example.demo.jwt;

import java.util.Date;

public class JwtClaims {
	
	private String jwt;

	private String id;
	
	private String subject;
	
	private Date expiration;
	
	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public JwtClaims() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtClaims(String jwt, String id, String subject, Date expiration) {
		super();
		this.jwt = jwt;
		this.id = id;
		this.subject = subject;
		this.expiration = expiration;
	}

	@Override
	public String toString() {
		return "JwtClaims [jwt=" + jwt + ", id=" + id + ", subject=" + subject + ", expiration=" + expiration + "]";
	}
	
}
