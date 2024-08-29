//package com.medilabo.diagnosis_gateway.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class GatewayMvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private AuthorizationInterceptor authorizationInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // Add the custom interceptor to all paths
//        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
//    }
//}