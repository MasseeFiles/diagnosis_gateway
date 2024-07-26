package com.medilabo.diagnosis_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> diagnosisPatientRoute1() {
        return GatewayRouterFunctions.route("diagnosisPatient")
                .route(RequestPredicates.GET("/patient"), HandlerFunctions.http("http://localhost:8081/patient"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> diagnosisPatientRoute2() {
        return RouterFunctions.route(
                RequestPredicates.GET("/patient/{id}"),
                request -> {
                    String patientId = request.pathVariable("id");
                    String targetUrl = "http://localhost:8081/patient/" + patientId;
                    return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
                }
        );
    }

    @Bean
    public RouterFunction<ServerResponse> diagnosisPatientRoute3() {
        return RouterFunctions.route(
                RequestPredicates.PUT("/patient/{id}"),
                request -> {
                    String patientId = request.pathVariable("id");
                    String targetUrl = "http://localhost:8081/patient/" + patientId;
                    return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
                }
        );
    }

    @Bean
    public RouterFunction<ServerResponse> diagnosisRiskRoute1() {
        return RouterFunctions.route(
                RequestPredicates.GET("/risk/{id}"),
                request -> {
                    String patientId = request.pathVariable("id");
                    String targetUrl = "http://localhost:8085/risk/" + patientId;
                    return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
                }
        );
    }

    @Bean
    public RouterFunction<ServerResponse> diagnosisNoteRoute1() {
        return RouterFunctions.route(
                RequestPredicates.GET("/note/{id}"),
                request -> {
                    String patientId = request.pathVariable("id");
                    String targetUrl = "http://localhost:8083/note/" + patientId;
                    return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
                }
        );
    }

    @Bean
    public RouterFunction<ServerResponse> diagnosisNoteRoute2() {
        return RouterFunctions.route(
                RequestPredicates.POST("/note/add/{id}"),
                request -> {
                    String patientId = request.pathVariable("id");
                    String targetUrl = "http://localhost:8083/note/add/" + patientId;
                    return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
                }
        );
    }
}