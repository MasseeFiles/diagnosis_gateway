//package com.medilabo.diagnosis_gateway.configuration;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException {
//        // Determine target URL based on some logic
//        String targetUrl = determineTargetUrl(authentication);
//
//        // Perform the redirect
//        redirectStrategy.sendRedirect(request, response, targetUrl);
//    }
//
//    private String determineTargetUrl(Authentication authentication) {
//        // Logic to determine where to redirect after successful login
//        return "/view";
//    }
//}
