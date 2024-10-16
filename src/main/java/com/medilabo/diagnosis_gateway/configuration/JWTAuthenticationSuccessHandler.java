package com.medilabo.diagnosis_gateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

// Custom Success Handler to generate JWT
@Component
public class JWTAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {

        //Generation du JWT Token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateToken(userDetails);  // generating a JWT token for the authenticated user
        System.out.println("SuccessHandler : JWT token created : " + jwtToken);

        // Add the JWT token to the response header
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Authorization", "Bearer " + jwtToken);



//        // Retrieve the original URI from the session
//        return exchange.getSession().flatMap(session -> {
//            String originalUri = (String) session.getAttributes().get("REQUEST_URI");
//
//            // Redirect the user to the original URI if it exists, otherwise default to a home page
//            if (originalUri != null) {
//                session.getAttributes().remove("REQUEST_URI");  // Clean up after redirect
//
////                return webFilterExchange.getExchange().getResponse().setComplete()
////                        .then(Mono.fromRunnable(() ->
////                                exchange.getResponse().setStatusCode(HttpStatus.FOUND)));
//                // Set the redirect location and status code (302 Found)
//                response.setStatusCode(HttpStatus.FOUND);
//                response.getHeaders().setLocation(URI.create(originalUri));
//
//                // Complete the response with the redirect
//                return response.setComplete();
//            }
            return response.setComplete();  // Marks the response handling as complete
//                    return Mono.empty();  // You can replace this with a redirect if needed
//        });
    }

}


