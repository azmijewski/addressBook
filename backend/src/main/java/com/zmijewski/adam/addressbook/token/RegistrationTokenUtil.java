package com.zmijewski.adam.addressbook.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class RegistrationTokenUtil {

    @Value("${jwt.registration.secretKey}")
    private String secretKey;
    private long expirationTime = 1000 * 60 * 60 * 24;

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public boolean validateToken(String token){
        Date expTime = getExpirationDateFromToken(token);
        return expTime.after(new Date(System.currentTimeMillis()));
    }
    public String generateToken(String username){
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
        Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }


}
