package com.company.mini_grocery.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey12";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean isTokenValid(String token, String email){

        String extractEmail = extractEmail(token);

        return  extractEmail.equals(email)
                && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration()
                .before(new Date());
    }
}
