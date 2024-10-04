package com.medilabo.diagnosis_gateway.configuration;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;

//token basé sur username
@Component
public class JwtTokenProvider {
    private String secretKey = ("MySecretKey");
    private SecretKey jwtSecretKey = getSigningKey(); // Generates a secure key
//        private final SecretKey jwtSecretKey = Keys.signWith(SignatureAlgorithm.HS512); // Generates a secure key
//    private final SecretKey jwtSecretKey = Keys.hmacShaKeyFor("mySecretKeyForJWTAuthentication12345678901234".getBytes(StandardCharsets.UTF_8));

    private SecretKey getSigningKey() {
//        return Jwts.SIG.HS256.key().build();
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date currentDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(System.currentTimeMillis() + 3600000);  //valide 1 heure

        String token = Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .and()
                .signWith(jwtSecretKey)
                .compact();

        return token;
    }

    // This method retrieves the token using the current Authentication
    public Mono<String> getToken() {
        // Recuperation du security context
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication())
                .map(this::generateToken); // Pass Authentication to generateToken
    }

    // extract username from JWT token
    public String getUsername(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) jwtSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

//    // validate JWT token
//    public boolean validateToken(String token) {
//        Jwts.parser()
//                .verifyWith((SecretKey) jwtSecretKey)
//                .build()
//                .parse(token);
//        return true;
//
//    }

    // Method to validate token and check for its validity
    public boolean validateToken(String token) {
        try {
            // Parse and validate the token
//            Jwts.parserBuilder()
            Jwts.parser()
                    .verifyWith(jwtSecretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // If no exception is thrown, the token is valid
            return true;

        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT token compact of handler are invalid");
        }
        // If any exception is thrown, the token is invalid
        return false;
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        // Use a predefined secret key for HS256
        SecretKey secretKey = Keys.hmacShaKeyFor("fhùmllLKLKHL§ùknùlkùlkqùmvwxùbjsFDJmokdsfCXVSozehfmqlhSDFV%SSDF".getBytes());
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }


}
