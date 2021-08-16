package com.example.demo.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtDecoder {

    Jws<Claims> decodeJwt(String jwt) throws JSONWebTokenException;

}
