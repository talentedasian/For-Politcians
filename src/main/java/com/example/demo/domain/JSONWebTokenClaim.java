package com.example.demo.domain;

public class JSONWebTokenClaim {

    private final String id, email, name;

    public String id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String name() {
        return name;
    }

    public JSONWebTokenClaim(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

}
