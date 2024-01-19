package com.login.logintest.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "AZW/5OwyaahAqcLJY0fcPy7qM4/dnqgrBHtlSq05aA+J/Uysrb/FxcEEdYAYNKpPY9W7OrqFEyHWXXh32yy/8JCaSIO5EYe9jAXc9SoNfCp1mo7RU5edGf30TjaxadXi4Ag5IsVAW8Ku0XcJpIPGSrNay/nQTLJ+pQ89i2xg+5dpUdQiO+ShRaV9bZDLRS5A2i54LUuEYStEXA0PDZao3LVrG+4vDva0Qmz/RSpCtoCPp29Z8Ie9DixAabEv7ASitktKU3bO6szOkpB4ezdt7wsBYChHXMWWxK2RvryL4WAZ9VV5a74xFT5MDErq4a8HuLcnKISRtrCGhTVeZ+V3sw9/jiJK9I/OJYAs23hWPCg=\n";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
      return Jwts.builder()
              .setClaims(extraClaims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
              .signWith(getSignInKey(), SignatureAlgorithm.HS256)
              .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return (Claims) Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parse(token)
                .getBody();
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
