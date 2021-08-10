package com.example.demo.adapter.in.web.jwt;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class JwtDto extends RepresentationModel<JwtDto>{
	
	private String jwt;

	private String id;
	
	private String subject;
	
	private LocalDateTime expiration;
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public LocalDateTime getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}

	public JwtDto() {}

	public JwtDto(String jwt, String id, String subject, LocalDateTime expiration, String name) {
		super();
		this.jwt = jwt;
		this.id = id;
		this.subject = subject;
		this.expiration = expiration;
		this.name = name;
	}

	@Override
	public String toString() {
		return "JwtDto [jwt=" + jwt + ", id=" + id + ", subject=" + subject + ", expiration=" + expiration + ", name=" + name + "]";
	}
	
}
