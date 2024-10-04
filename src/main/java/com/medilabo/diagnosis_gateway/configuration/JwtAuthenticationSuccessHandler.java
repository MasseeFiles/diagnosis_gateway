//package com.medilabo.diagnosis_gateway.configuration;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    public JwtAuthenticationSuccessHandler(JwtTokenUtil jwtTokenUtil) {
//        this.jwtTokenUtil = jwtTokenUtil;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        String username = authentication.getName();
//        String token = jwtTokenUtil.generateToken(username);
//
//        // Add the token to the response
//        response.setContentType("application/json");
//        response.getWriter().write("{\"token\": \"" + token + "\"}");
//        response.getWriter().flush();
//    }
//}
