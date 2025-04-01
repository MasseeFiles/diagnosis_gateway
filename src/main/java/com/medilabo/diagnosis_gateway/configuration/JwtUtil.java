package com.medilabo.diagnosis_gateway.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
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
 * Authentication à partir du JWT validé.
 */
@Component
public class JwtUtil {

    @Value("${JWT_SECRET}")
    private String SECRET;

    private static final long VALIDITY_DURATION = TimeUnit.MINUTES.toMillis(60);

    // Extraction et validation du token (verification de son authenticité par sa signature)
    public Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        Claims claims = Jwts.parser()
                // Validation du token par comparaison de signatures: renvoie une JWT exception si le token est invalide
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims;
    }

    // Verification de l'expiration du JWT
    public boolean isTokenValid(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

    public String generateToken(UserDetails userDetails) {
        //Creation de la clef utilisée pour signer le JWT.
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        // Extraction du role à partir du userDetails
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)  // Get the role name
                .collect(Collectors.joining(","));

        Map<String, String> claims = new HashMap<>();
        claims.put("roles", roles);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + VALIDITY_DURATION))
                .signWith(key)
                .compact();
    }

    // Creation d'un objet Authentication à partir du JWT validé.
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

