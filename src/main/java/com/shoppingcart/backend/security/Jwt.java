package com.shoppingcart.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Jwt {
    public static final long EXPIRATION_TIME = 864000000; //10 DAYS
    public String encode(String payload){
        return Jwts
            .builder()
            .setSubject(payload)
            .setExpiration(getExpiration())
            .signWith(SignatureAlgorithm.HS512, getTokenSecret())
            .compact();
    }

    private String getTokenSecret(){
        return "password";
    }

    private Date getExpiration(){
        return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    }

    public Claims decode(String token) {
        return Jwts
            .parser()
            .setSigningKey(getTokenSecret())
            .parseClaimsJws(token)
            .getBody();
    }
}
