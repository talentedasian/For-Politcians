package com.example.demo.jwt;

import java.security.KeyPair;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtKeys {

	private final static KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
		
	public static final KeyPair getJwtKeyPair() {
		return keyPair;
	}
}
