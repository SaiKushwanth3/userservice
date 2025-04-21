package com.userservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET = "PK4CU3DBWAHSDydh43QU+F8Zsj+n6M0rmnxQcQCO8gBtFKx4NpAJNsjsNGxLqmdB";

    public String generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,userName);
    }
    private String  createToken(Map<String,Object> claims, String userName){
        return Jwts.builder()
                .subject(userName)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+(1000*60*30)))
                .signWith(getSignKey())
                .compact();
    }
    public boolean validateToken(String token, UserDetails userDetails){
        String userName = extractUserName(token);
        return userDetails.getUsername().equals(userName) && !isTokenExpired(token);
    }

    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
