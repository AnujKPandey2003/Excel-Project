package com.example.excel.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.excel.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET;

    // ✅ Extract username (email)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Extract name
    public String extractName(String token) {
        return extractAllClaims(token).get("name", String.class);
    }

    // ✅ Extract userId
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    // ✅ Extract expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ✅ Generic claim extractor
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // ✅ Parse all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .setAllowedClockSkewSeconds(300)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Check expiry
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ✅ Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // ✅ Generate token using full User object (now includes name)
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName()); // 👈 added name
        return createToken(claims, user.getEmail());
    }

    // ✅ Generate token using only email and name (for simplified flow)
    public String generateToken(String email, String name) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("name", name);
        return createToken(claims, email);
    }

    // ✅ Private helper to create JWT
    private String createToken(Map<String, Object> claims, String subject) {
        long now = System.currentTimeMillis();
        long expiry = 1000L * 60 * 60; // 1 hour
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiry))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Get signing key
private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
}
}
