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

    //Liste des patients - entry point
    @Bean
    public RouterFunction<ServerResponse> gatewayToViewRoute1() {
        return GatewayRouterFunctions.route("view")  // voir convention rest nommage but : /patients
                .route(RequestPredicates.GET("/view"), HandlerFunctions.http("http://localhost:8082/list"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> gatewayToViewRoute111() {
        return GatewayRouterFunctions.route("list")  // voir convention rest nommage but : /patients
                .route(RequestPredicates.GET("/list"), HandlerFunctions.http("http://localhost:8082/list"))
                .build();
    }

    //affichage Formulaire update Patient - redirection vers la view
    //pas besoin, la vue veut juste les details d'un patient dans sa requete
//    @Bean
//    public RouterFunction<ServerResponse> gatewayToViewRoute2() {
//        return RouterFunctions.route(
//                RequestPredicates.POST("/updateForm/{id}"),
//                request -> {
//                    String patientId = request.pathVariable("id");
//                    String targetUrl = "http://localhost:8082/updateForm/" + patientId;
//                    return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
//                }
//        );
//    }

    //Liste des patients - micro patient

    @Bean
    public RouterFunction<ServerResponse> gatewayToPatientRoute1() {
        return GatewayRouterFunctions.route("patient")
                .route(RequestPredicates.GET("/patient"), HandlerFunctions.http("http://localhost:8081/patient"))
                .build();
    }

    //recherhce patient unique
    @Bean
    public RouterFunction<ServerResponse> gatewayToPatientRoute2() {
        return RouterFunctions.route(
                RequestPredicates.GET("/patient/{id}"),
                request -> {
                    String patientId = request.pathVariable("id");
                    String targetUrl = "http://localhost:8081/patient/" + patientId;
                    return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
                }
        );
    }

    //persistence des données d'un patient
    @Bean
    public RouterFunction<ServerResponse> gatewayToPatientRoute3() {
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
    public RouterFunction<ServerResponse> gatewayToRiskRoute1() {
        return RouterFunctions.route(
                RequestPredicates.GET("/risk/{id}"),
                request -> {
                    String patientId = request.pathVariable("id");
                    String targetUrl = "http://localhost:8085/risk/" + patientId;
                    return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
                }
        );
    }

    //Voir Notes du patient
    @Bean
    public RouterFunction<ServerResponse> gatewayToNoteRoute1() {
        return RouterFunctions.route(
                RequestPredicates.GET("/note/{id}"),
                request -> {
                    String patientId = request.pathVariable("id");
                    String targetUrl = "http://localhost:8083/note/" + patientId;
                    return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
                }
        );
    }

    //persister note en base
    @Bean
    public RouterFunction<ServerResponse> gatewayToNoteRoute2() {
        return RouterFunctions.route(
                RequestPredicates.POST("/note/add/{id}"),
                request -> {
                    String patientId = request.pathVariable("id");
                    String targetUrl = "http://localhost:8083/note/add" + patientId;
                    return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
                }
        );
    }



}