package com.medilabo.diagnosis_gateway.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Utilty class fournissant les méthodes nécessaires à la manipulation du JWT : validation,
 * extraction des claims, vérification de l'expiration du token et generation d'un objet
 * Authentication.
 */
@Component
public class JwtUtil {

    private static final String SECRET = "9F44D4C546D4DA5B4mldkvmdjmwdjvmqlkdvqùlvùV%kjB4D9898881F3";
    private static final long VALIDITY_DURATION = TimeUnit.MINUTES.toMillis(60);

    public Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                //method de validation du token avec clef fournie : renvoie une JWT exception si la signature est invalide
                .parseSignedClaims(token)
                .getPayload();

        return claims;
    }

    // Check token expiration
    public boolean isTokenValid(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

//  a voir si plus pertinent
//    // Validate the token by checking the username and expiration date
//    private boolean validateToken(String token, UserDetails userDetails) {
//        String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

    // Generate a JWT token
    public String generateToken(UserDetails userDetails) {
        //generation d'une clef pour la signature
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

//        // Extraction du role à partir du userDetails
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)  // Get the role name
                .collect(Collectors.joining(","));

        Map<String, String> claims = new HashMap<>();
        claims.put("roles", roles);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", roles)  // Add roles as a claim
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + VALIDITY_DURATION))
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = extractClaims(token);
        String username = claims.getSubject();
        String roles = claims.get("roles", String.class);

        List<GrantedAuthority> authorities = Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

}

