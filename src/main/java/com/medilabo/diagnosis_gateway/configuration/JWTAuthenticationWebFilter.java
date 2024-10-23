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

/**
 * Filter responsable de la gestion des tokens JWT transmis par les requetes HTTP entrantes.
 * <p>
 * Il permet de vérifier si un token JWT est présent dans la requete,
 * de le valider, et de retourner un objet Authentication dans le
 * SecurityContextHolder basé sur les claims du token.
 *
 * @see JwtUtil
 */
@Component
public class JWTAuthenticationWebFilter implements WebFilter {

    private static final Logger logger = LogManager.getLogger("JWTAuthenticationWebFilter");

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String jwtToken = null;

        // Recuperation du token JWT si present
        if (exchange.getRequest().getCookies().getFirst("jwt-token") != null) {
            jwtToken = exchange.getRequest().getCookies().getFirst("jwt-token").getValue();

            logger.info("JWT token trouvé dans la requete entrante: " + jwtToken);
        }

        // Validation du token JWT et creation d'un objet authentication mis dans le security context
        if (jwtToken != null && jwtUtil.isTokenValid(jwtToken)) {
            Authentication authentication = jwtUtil.getAuthentication(jwtToken);
            SecurityContext securityContext = new SecurityContextImpl(authentication);

            ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext));

            // Inclusion du token JWT dans les requetes sortantes au niveau du Authorization header
            ServerHttpRequest modifiedRequest = exchange.getRequest()
                    .mutate()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        }

        // Continuation de la filter chain si le JWT token n'est pas trouvé
        logger.info("Pas de JWT token trouvé dans la requete entrante.");

        return chain.filter(exchange);
    }

    private String extractToken(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Suppression du prefix "Bearer "
        }
        return null;
    }

}

