//package com.medilabo.diagnosis_gateway.configuration;
//
//import io.jsonwebtoken.Claims;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class AuthenticationFilter implements GatewayFilter {
//
//    //    private final RouterValidator routerValidator;
//    private final JwtUtil jwtUtil;
//
//    @Autowired
//    public AuthenticationFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//
//// methode pour implementer le filtrage de chaque request
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//
//        if (this.isAuthMissing(request)) {
//            return this.onError(exchange, HttpStatus.UNAUTHORIZED);
//        }
//
//        final String token = this.getAuthHeader(request);
//
//        if (jwtUtil.isInvalid(token)) {
//            return this.onError(exchange, HttpStatus.FORBIDDEN);
//        }
//
//        this.updateRequest(exchange, token);
////    }
//        return chain.filter(exchange);
//}
//
//    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(httpStatus);
//        return response.setComplete();
//    }
//
//    private String getAuthHeader(ServerHttpRequest request) {
//        return request.getHeaders().getOrEmpty("Authorization").get(0);
//    }
//
//    private boolean isAuthMissing(ServerHttpRequest request) {
//        return !request.getHeaders().containsKey("Authorization");
//    }
//
//    private void updateRequest(ServerWebExchange exchange, String token) {
//        Claims claims = jwtUtil.getAllClaimsFromToken(token);
//        exchange.getRequest().mutate()
//                .header("email", String.valueOf(claims.get("email")))
//                .build();
//    }
//}