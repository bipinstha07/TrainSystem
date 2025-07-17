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

@Component
public class JwtHelper {

    public static final int validity = 15 * 60 * 1000;  //  5 minutes in millisecond
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
@SuppressWarnings("deprecation")
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key,SignatureAlgorithm.HS512) // key already knows algorithm
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
