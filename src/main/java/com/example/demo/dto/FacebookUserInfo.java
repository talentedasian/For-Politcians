package com.example.demo.dto;

public class FacebookUserInfo {
	
	private String id, email, name;
		
	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public FacebookUserInfo(String id, String email, String name) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
	}

	public FacebookUserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
