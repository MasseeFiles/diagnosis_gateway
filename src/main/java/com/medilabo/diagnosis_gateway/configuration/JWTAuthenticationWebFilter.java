package com.medilabo.diagnosis_gateway.configuration;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Filter responsable de la gestion des tokens JWT transmis par les requetes HTTP entrantes.
 *
 * Il permet de vérifier si un token JWT est présent dans le Authorization header de la requete,
 * de le valider, et de retourner un objet Authentication dans le
 * SecurityContextHolder basé sur les claims du token.
 * @see JwtUtil
 */
@Component
public class JWTAuthenticationWebFilter implements WebFilter {

    private static final Logger logger = LogManager.getLogger("JWTAuthenticationWebFilter");

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        //add cookie
        ServerHttpRequest request = exchange.getRequest();

        //add cookie
        // 1. Try to get the JWT from the cookie
        String jwtToken = exchange.getRequest().getCookies().getFirst("jwt-token") != null ?
                exchange.getRequest().getCookies().getFirst("jwt-token").getValue() : null;

        logger.info("Bearer token found in the cookie : " + jwtToken);


        // 1. Try to get the JWT from the authentication header
        if (jwtToken == null) {
            List<String> authHeaders = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION);
            if (!authHeaders.isEmpty() && authHeaders.get(0).startsWith("Bearer ")) {
                jwtToken = authHeaders.get(0).substring(7);  // Remove "Bearer " prefix
            }
        }

//        //avant add cookie
//        String token = extractToken(exchange);

        logger.info("Bearer token found in incoming request : " + jwtToken);

        // Validate the JWT jwtToken, build an authentication object with it , and put it in the security context
        if (jwtToken != null && jwtUtil.isTokenValid(jwtToken)) {
            // Create an Authentication object if the JWT is valid
            Authentication authentication = jwtUtil.getAuthentication(jwtToken);
            SecurityContext securityContext = new SecurityContextImpl(authentication);

            // Set the security context with the authenticated user
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
        }

        // Continue the filter chain if no valid token is found
        return chain.filter(exchange);
    }

    // Extract the token from the Authorization header
    private String extractToken(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Remove "Bearer " prefix
        }
        return null;
    }

}

