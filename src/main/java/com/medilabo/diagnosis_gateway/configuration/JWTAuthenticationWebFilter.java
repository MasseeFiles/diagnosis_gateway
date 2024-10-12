package com.medilabo.diagnosis_gateway.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

// filter to handle the JWT from subsequent requests.

//Extracting the JWT from the Authorization header.
//        Validating the token - le met dans le securitycontextholder
//        Creating an Authentication object if the token is valid and setting it in the security context.
@Component
public class JWTAuthenticationWebFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Extract JWT token from the Authorization header
        String token = extractToken(exchange);

        // Validate the JWT token
        if (token != null && jwtUtil.isTokenValid(token)) {
            // Create an Authentication object if the JWT is valid
            Authentication authentication = jwtUtil.getAuthentication(token);
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

