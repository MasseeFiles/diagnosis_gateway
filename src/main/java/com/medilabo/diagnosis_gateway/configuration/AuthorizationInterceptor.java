//package com.medilabo.diagnosis_gateway.configuration;
//
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthorizationInterceptor implements HandlerInterceptor {
//    //version qui renvoi parfois le token dans le header
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // Extract Authorization header
//        String authorizationHeader = request.getHeader("Authorization");
//
//        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
//            System.out.println("Retrieved authorization token from incoming request: " + authorizationHeader);
//
//            // Pass the Authorization header to downstream services
//            request.setAttribute("Authorization", authorizationHeader);
//        } else {
//            System.out.println("No token found from incoming request");
//
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
//        return true;
//    }
//
//}
