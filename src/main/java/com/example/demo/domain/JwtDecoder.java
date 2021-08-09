package com.example.demo.domain;

public interface JwtDecoder {

    JSONWebTokenClaim decodeJwt(String jwt) throws JSONWebTokenException;

}
