//package com.medilabo.diagnosis_gateway.configuration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Map;
//
//@Component
//public class CustomAuthenticationConverter implements ServerAuthenticationConverter {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public Mono<Authentication> convert(ServerWebExchange exchange) {
//        return exchange.getRequest().getBody()
//                .next()
//                .flatMap(dataBuffer -> {
//                    try {
//                        // Convertir le corps de la requête en une Map avec username et password
//                        Map<String, String> body = objectMapper.readValue(dataBuffer.asInputStream(), Map.class);
//                        String username = body.get("username");
//                        String password = body.get("password");
//
//                        // Créer l'objet Authentication à partir des informations fournies
//                        UsernamePasswordAuthenticationToken authRequest =
//                                new UsernamePasswordAuthenticationToken(username, password);
//                        return Mono.just(authRequest);
//                    } catch (Exception e) {
//                        return Mono.error(new AuthenticationException("Failed to parse authentication request body") {
//                        });
//                    }
//                });
//    }
//}
