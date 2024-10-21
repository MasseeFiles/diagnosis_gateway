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


@Component
public class JWTAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private static final Logger logger = LogManager.getLogger("JWTAuthenticationSuccessHandler");

    @Autowired
    private JwtUtil jwtUtil;

//    @Value("${VIEW_IP}")
//    private String viewIp;
    private String viewIp = "localhost";

//    @Value("${VIEW_PORT}")
//    private String viewPort;
    private String viewPort = "8084";

    @Override
    public Mono onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {

// Generate JWT token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateToken(userDetails);

        logger.info("SuccessHandler : JWT token created : " + jwtToken);

// Add JWT token to response header
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Authorization", "Bearer " + jwtToken);

// Build the full URL for redirection using the VIEW_IP and VIEW_PORT from properties
//        String redirectUrl = "http://" + viewIp + ":" + viewPort + "/view/patientList";

//        String redirectUrl = "http://localhost" + ":" + viewPort + "/view/patientList";
//
//        String redirectUrl = "http://localhost:" + viewPort + "/view/patientList?token=" + jwtToken;

        //add cookie

        // Create an HttpOnly cookie to store the JWT token
        ResponseCookie jwtCookie = ResponseCookie.from("jwt-token", jwtToken)
                .httpOnly(true)  // Makes it inaccessible to JavaScript
                .secure(true)    // Ensures it is only sent over HTTPS
                .path("/")       // Path to which the cookie applies
                .maxAge(Duration.ofHours(1))  // Set token expiration
                .build();

// Add the cookie to the response
        response.addCookie(jwtCookie);



//        response.setStatusCode(HttpStatus.FOUND);
//        response.getHeaders().setLocation(URI.create(redirectUrl));

        return response.setComplete();
    }

}


//    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String jwtToken = jwtUtil.generateToken(userDetails);
//
//        logger.info("JWT token created : " + jwtToken);
//
//        // Add the JWT token to the response header
//        ServerWebExchange exchange = webFilterExchange.getExchange();
//        ServerHttpResponse response = exchange.getResponse();
//        response.getHeaders().set("Authorization", "Bearer " + jwtToken);
//
//        // Always redirect to /view/patientList after success
//        response.setStatusCode(HttpStatus.FOUND);
//        response.getHeaders().setLocation(URI.create("/view/patientList"));
//
//        String viewServiceUrl = "http://" + viewIp + ":" + viewPort + "/view/patientList";
//        response.getHeaders().setLocation(URI.create(viewServiceUrl));
//
//
//
//
//        return response.setComplete();
//    }
//}










//
//
//
//// Custom Success Handler to generate JWT
//@Component
//public class JWTAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
//
//    private static final Logger logger = LogManager.getLogger("JWTAuthenticationSuccessHandler");
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Override
//    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
//
//        //Generation du JWT Token
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        // generating a JWT token for the authenticated user
//        String jwtToken = jwtUtil.generateToken(userDetails);
//
//        logger.info("JWT token created : " + jwtToken);
//
//        // Add the JWT token to the response header
//        ServerWebExchange exchange = webFilterExchange.getExchange();
//        ServerHttpResponse response = exchange.getResponse();
//        response.getHeaders().set("Authorization", "Bearer " + jwtToken);
//
////        // Retrieve the original URI from the session
////        return exchange.getSession().flatMap(session -> {
////            String originalUri = (String) session.getAttributes().get("REQUEST_URI");
////
////            // Redirect the user to the original URI if it exists, otherwise default to a home page
////            if (originalUri != null) {
////                session.getAttributes().remove("REQUEST_URI");  // Clean up after redirect
////
//////                return webFilterExchange.getExchange().getResponse().setComplete()
//////                        .then(Mono.fromRunnable(() ->
//////                                exchange.getResponse().setStatusCode(HttpStatus.FOUND)));
////                // Set the redirect location and status code (302 Found)
////                response.setStatusCode(HttpStatus.FOUND);
////                response.getHeaders().setLocation(URI.create(originalUri));
////
////                // Complete the response with the redirect
////                return response.setComplete();
//
////            }
//        //                session.getAttributes().remove("REQUEST_URI");  // Clean up after redirect
//                response.setStatusCode(HttpStatus.FOUND);
//
//        response.getHeaders().setLocation(URI.create("/view/patientList"));
//
//            return response.setComplete();  // Marks the response handling as complete
////                    return Mono.empty();  // You can replace this with a redirect if needed
////        });
//    }
//
//}
//
//
