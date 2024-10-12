package com.medilabo.diagnosis_gateway.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

//    private static final String SECRET = "YourSecretKeyForJWTSignin";
    private static final String SECRET = "9F44D4C546D4DA5B4mldkvmdjmwdjvmqlkdvqùlvùV%kjB4D9898881F3";
    private static final long VALIDITY_DURATION = TimeUnit.MINUTES.toMillis(60); //validite du token - 60 min

    // Parse and validate the token
    public Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims;
    }

    // Check token expiration
    public boolean isTokenValid(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration().after(new Date());
    }

    // Generate a JWT token
    public String generateToken(UserDetails userDetails) {
        //generation d'une clef pour la signature
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

//        // Extract roles from the userDetails
//        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//        // Convert roles to a list of string role names
//        String roles = authorities.stream()
//                .map(GrantedAuthority::getAuthority)  // Get the role name
//                .collect(Collectors.joining(","));
//        Map<String, String> claims = new HashMap<>();
//        claims.put("roles", roles);


        return Jwts.builder()
                .subject(userDetails.getUsername())
//                .claim("roles", roles)  // Add roles as a claim
                .issuedAt(new Date(System.currentTimeMillis()))
                //date d'expiration : 1h
                .expiration(new Date(System.currentTimeMillis() + VALIDITY_DURATION))
                .signWith(key)
                .compact();
    }

    // Create Authentication object from JWT token
    public Authentication getAuthentication(String token) {
        Claims claims = extractClaims(token);
        String username = claims.getSubject();

        //pas de recherche du password : jamais indiqué dans le token

//        // Extract roles or other claims if available
//        List<String> roles = claims.get("roles", List.class);  // Assume roles are stored in a "roles" claim
//        List<GrantedAuthority> authorities = roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
        //modifier recuperation du role Collections.emptyList()

        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    }
}

