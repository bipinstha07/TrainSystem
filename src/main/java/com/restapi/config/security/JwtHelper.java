package com.restapi.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtHelper {

    public static final long accessValidity = 15 * 60 * 1000;  //  15 minutes in millisecond
    private static final long refreshValidity = 600 * 60 * 1000;
    private final String secret = "yourSuperSecretKeyForHS512AlgorithmWhichMustBeAtLeast64BytesLongAndSecure"; // Changed to a 64-byte string example


    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

//    public String generateToken(UserDetails userDetails){
//        return Jwts.builder().setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis()+validity))
//                .signWith(key, SignatureAlgorithm.HS512)
//                .compact();
//    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("token_type","accessToken");
        return buildToken(claims,userDetails.getUsername(),accessValidity);
    }

    public String generateRefreshToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put("token_type","refreshToken");
        return buildToken(claims,userDetails.getUsername(),refreshValidity);
    }


    public boolean isRefreshToken(String token){
        return getTokenType(token).equals("refreshToken");
    }

    public boolean isAccessToken(String token){
        return getTokenType(token).equals("accessToken");
    }

    private String getTokenType(String token){
       Object tokenType =   getClaims(token).get("token_type");
       return tokenType != null ? tokenType.toString() : "";
    }


    private String buildToken(Map<String,Object> claims, String subject, long validity){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key) // key already knows algorithm
                .compact();
    }


    public String getUserNameFromToken(String token){
        return getClaims(token).getSubject();
    }


    public boolean isTokenValid(String token,UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }



    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)       // replaces setSigningKey
                .build()
                .parseSignedClaims(token)
                .getPayload();         // replaces getBody()
    }

}
