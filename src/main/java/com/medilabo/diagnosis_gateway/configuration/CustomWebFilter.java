//package com.medilabo.diagnosis_gateway.configuration;
//
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@Component
//public class CustomWebFilter implements WebFilter {
//
//        @Override
//        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//            // Log the beginning of the request handling
//            System.out.println("Processing request: " + exchange.getRequest().getURI());
//
//            return ReactiveSecurityContextHolder.getContext()
//                    .flatMap(securityContext -> {
//                        // Retrieve the Authentication object
//                        Authentication authentication = securityContext.getAuthentication();
//                        String authToken;
//
//                        // Check if authentication exists and is authenticated
//                        if (authentication != null && authentication.isAuthenticated()) {
//                            // Assuming the token is stored in the Authentication object
//                            authToken = (String) authentication.getCredentials();
//                            System.out.println("Authentication token found in incoming request: " + authToken);
//
//                        // If you want to add the token to the request headers for downstream services
//                            // Mutate the request to add the Authorization header
//                            ServerWebExchange mutatedExchange = exchange.mutate()
//                                    .request(request -> request.headers(headers -> {
//                                        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
////                                        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + authToken);
//                                    }))
//                                    .build();
//
//                            // Proceed with the mutated exchange
//                            return chain.filter(mutatedExchange);
//                        } else {
//                            authToken = null;
//                            // Proceed with the next filter or handler
//
//                            return chain.filter(exchange);
//                        }
//                    })
//                    .switchIfEmpty(chain.filter(exchange)) // Continue without token if SecurityContext is empty
//
//                    .doOnError(throwable -> {
//                        // Handle error in retrieving the security context
//                        System.err.println("Error retrieving SecurityContext: " + throwable.getMessage());
//                    });
//        }
//
//    }
//
//
//
