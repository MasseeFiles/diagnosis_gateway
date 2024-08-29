package com.medilabo.diagnosis_gateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class Routes {

    //   VIEW Service routes
    //    demande affichage des infos de tous les patients - redirection vers la view
    @Bean
    public RouteLocator gatewayToViewRoute1(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("view_service_1", r -> r
                        .method(HttpMethod.GET) // Match GET requests
                        .and()
                        .path("/viewService/patientList") // Define the path predicate
                        .uri("http://localhost:8082") // Define the URI to forward to
                )
                .build();
    }

        //    affichage Formulaire update Patient - redirection vers la view
    @Bean
    public RouteLocator gatewayToViewRoute2(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("view_service_2", r -> r
                        .method(HttpMethod.GET) // Match GET requests
                        .and()
                        .path("/viewService/updateFormPatient/{id}") // Define the path predicate
                        .uri("http://localhost:8082") // Define the URI to forward to
                )
                .build();
    }

        //    demande de persistence en base de patient  - redirection vers la view
    @Bean
    public RouteLocator gatewayToViewRoute3(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("view_service_3", r -> r
                        .method(HttpMethod.POST) // Match GET requests
                        .and()
                        .path("/viewService/updatePatient/{id}") // Define the path predicate
                        .uri("http://localhost:8082") // Define the URI to forward to
                )
                .build();
    }

        //Voir toutes les Notes du patient - concerne view
    @Bean
    public RouteLocator gatewayToViewRoute4(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("view_service_4", r -> r
                        .method(HttpMethod.GET) // Match GET requests
                        .and()
                        .path("/viewService/listNote/{id}") // Define the path predicate
                        .uri("http://localhost:8082") // Define the URI to forward to
                )
                .build();
    }

    //Voir formulaire NoteForm - concerne view
    @Bean
    public RouteLocator gatewayToViewRoute5(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("view_service_5", r -> r
                        .method(HttpMethod.POST) // Match GET requests
                        .and()
                        .path("/viewService/noteForm/{id}") // Define the path predicate
                        .uri("http://localhost:8082") // Define the URI to forward to
                )
                .build();
    }

        //persister une notes du patient - concerne view
    @Bean
    public RouteLocator gatewayToViewRoute6(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("view_service_6", r -> r
                        .method(HttpMethod.POST) // Match GET requests
                        .and()
                        .path("/viewService/addNote") // Define the path predicate
                        .uri("http://localhost:8082") // Define the URI to forward to
                )
                .build();
    }


//PATIENT service
    //Liste des data des tous les patients - micro patient
    @Bean
    public RouteLocator gatewayToPatientRoute1(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("patient_service_1", r -> r
                        .method(HttpMethod.GET) // Match GET requests
                        .and()
                        .path("/patientService") // Define the path predicate
                        .uri("http://localhost:8081") // Define the URI to forward to
                )
                .build();
    }

//    //recherche patient unique
    @Bean
    public RouteLocator gatewayToPatientRoute2(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("patient_service_2", r -> r
                        .method(HttpMethod.GET) // Match GET requests
                        .and()
                        .path("/patientService/{id}") // Define the path predicate
                        .uri("http://localhost:8081") // Define the URI to forward to
                )
                .build();
    }

        //persistence des données d'un patient
    @Bean
    public RouteLocator gatewayToPatientRoute3(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("patient_service_3", r -> r
                        .method(HttpMethod.PUT) // Match GET requests
                        .and()
                        .path("/patientService/{id}") // Define the path predicate
                        .uri("http://localhost:8081") // Define the URI to forward to
                )
                .build();
    }

        //NOTE service
    //recuperer notes du patient - concerne micro note
    @Bean
    public RouteLocator gatewayToNoteRoute1(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("note_service_1", r -> r
                        .method(HttpMethod.GET) // Match GET requests
                        .and()
                        .path("/noteService/{id}") // Define the path predicate
                        .uri("http://localhost:8083") // Define the URI to forward to
                )
                .build();
    }

  //persister note en base  - concerne micro note
    @Bean
    public RouteLocator gatewayToNoteRoute2(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("note_service_2", r -> r
                        .method(HttpMethod.POST) // Match GET requests
                        .and()
                        .path("/noteService/add/{id}") // Define the path predicate
                        .uri("http://localhost:8083") // Define the URI to forward to
                )
                .build();
    }

    @Bean
    public RouteLocator gatewayToRiskRout1(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("risk_service_route", r -> r
                                .method(HttpMethod.GET) // Match GET requests
                                .and()
                                .path("/riskService/{id}")
                                .uri("http://localhost:8085")
                )
                .build();
    }
}