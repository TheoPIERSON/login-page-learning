package com.login.logintest.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "lTENDhKZD27cTyTW+Rgsv/cGiudUI285PVy6p5yqZyFZ9nIzaJq+ofZQ7Ns2aUV82F93LhU1JC+SPvyWPc57zfkQXJsqklcBSXvyvUbn3JitoaI8l1m0aODnp/EcuwyxUVGzB9DMnuOHDNOGQX6mrvg5o1GjCBvxS6aa8Od6v1M5vQNEZalaCiwE5A5VkDDvQ+V/+hpdBov3dVMujGWPjeckzR7Cb+c5dvtCh9r9zjWaaD7C9+zSxEgld84yTXJNrFgplHGPZWtg7pmCSKf3RWXZZXDpNbCIthPlFCKMMgvND1pbF0xEBsrqJV/WOVrgQzIFhZ/ZH8Ps3LZAicAcRTb+jWWsyeFYa1SX8MqEChI=\n";

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


