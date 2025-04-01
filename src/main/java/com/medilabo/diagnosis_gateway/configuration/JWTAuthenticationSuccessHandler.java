package com.medilabo.diagnosis_gateway.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

/**
 * Classe utilisée pour gérer les actions à effectuer après une authentification réussie :
 * generation du JWT, renvoi dans la réponse du JWT et redirection.
 *
 * @see SpringSecurityConfig
 */

@Component
public class JWTAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private static final Logger logger = LogManager.getLogger("JWTAuthenticationSuccessHandler");

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${GATEWAY_PORT}")   // Transmis par le docker-compose
    private String redirectPort;
//private String redirectPort = "8084";

    @Override
    public Mono onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generation du JWT token
        String jwtToken = jwtUtil.generateToken(userDetails);

        logger.info("Authentication successful : JWT token created : " + jwtToken);

        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();

        // Creation d'un cookie HttpOnly contenant le JWT token pour l'ajouter à la réponse
        ResponseCookie jwtCookie = ResponseCookie.from("jwt-token", jwtToken)
                .httpOnly(true)  // Inaccessible à JavaScript
                .secure(true)    // Envoi par HTTPS
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();

        response.addCookie(jwtCookie);

        // Redirection après authentification reussie
        String redirectUrl = "http://localhost:" + redirectPort + "/view/patientList";

        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().setLocation(URI.create(redirectUrl));

        return response.setComplete();
    }

}

