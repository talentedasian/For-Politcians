package com.example.demo.domain;

import java.time.LocalDateTime;

public class JSONWebTokenClaim {

    private final String id, email, name;

    private final LocalDateTime expiration;

    public String id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String name() {
        return name;
    }

    public LocalDateTime expiration() {
        return expiration;
    }

    public JSONWebTokenClaim(String id, String email, String name, LocalDateTime expiration) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.expiration = expiration;
    }
}
