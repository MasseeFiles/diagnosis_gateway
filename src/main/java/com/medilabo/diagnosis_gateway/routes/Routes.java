package com.medilabo.diagnosis_gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class Routes {
    /**
     * Gateway to View microservice routes :
     * Redirections vers le microservice diagnosis-view
     */
    @Value("${VIEW_IP}")
    private String viewIp;

    @Value("${VIEW_PORT}")
    private int viewPort;

    @Bean
    public RouteLocator gatewayToViewRoute1(RouteLocatorBuilder builder) {
        String uriView = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(viewIp)
                .port(viewPort)
                .build()
                .toUriString();

        return builder.routes()
                .route("view_service_1", r -> r
                        .method(HttpMethod.GET)
                        .and()
                        .path("/view/patientList")
                        .uri(uriView)
                )
                .build();
    }

    @Bean
    public RouteLocator gatewayToViewRoute2(RouteLocatorBuilder builder) {
        String uriView = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(viewIp)
                .port(viewPort)
                .build()
                .toUriString();

        return builder.routes()
                .route("view_service_2", r -> r
                        .method(HttpMethod.GET)
                        .and()
                        .path("/view/updateFormPatient/{id}")
                        .uri(uriView)
                )
                .build();
    }

    @Bean
    public RouteLocator gatewayToViewRoute3(RouteLocatorBuilder builder) {
        String uriView = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(viewIp)
                .port(viewPort)
                .build()
                .toUriString();

        return builder.routes()
                .route("view_service_3", r -> r
                        .method(HttpMethod.POST)
                        .and()
                        .path("/view/updatePatient/{id}")
                        .uri(uriView)
                )
                .build();
    }

    @Bean
    public RouteLocator gatewayToViewRoute4(RouteLocatorBuilder builder) {
        String uriView = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(viewIp)
                .port(viewPort)
                .build()
                .toUriString();

        return builder.routes()
                .route("view_service_4", r -> r
                                .method(HttpMethod.GET)
                                .and()
                                .path("/view/listNote/{id}")
                                .uri(uriView)
                )
                .build();
    }

    @Bean
    public RouteLocator gatewayToViewRoute5(RouteLocatorBuilder builder) {
        String uriView = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(viewIp)
                .port(viewPort)
                .build()
                .toUriString();

        return builder.routes()
                .route("view_service_5", r -> r
                        .method(HttpMethod.POST)
                        .and()
                        .path("/view/noteForm/{id}")
                        .uri(uriView)
                )
                .build();
    }

    @Bean
    public RouteLocator gatewayToViewRoute6(RouteLocatorBuilder builder) {
        String uriView = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(viewIp)
                .port(viewPort)
                .build()
                .toUriString();

        return builder.routes()
                .route("view_service_6", r -> r
                        .method(HttpMethod.POST)
                        .and()
                        .path("/view/addNote")
                        .uri(uriView)
                )
                .build();
    }

    /**
     * Gateway to Patient microservice routes :
     * Redirections vers le microservice diagnosis-patient
     */
    @Value("${PATIENT_IP}")
    private String patientIp;

    @Value("${PATIENT_PORT}")
    private int patientPort;

    @Bean
    public RouteLocator gatewayToPatientRoute1(RouteLocatorBuilder builder) {
        String uriPatient = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(patientIp)
                .port(patientPort)
                .build()
                .toUriString();

        return builder.routes()
                .route("patient_service_1", r -> r
                        .method(HttpMethod.GET)
                        .and()
                        .path("/patients")
                        .uri(uriPatient)
                )
                .build();
    }

    @Bean
    public RouteLocator gatewayToPatientRoute2(RouteLocatorBuilder builder) {
        String uriPatient = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(patientIp)
                .port(patientPort)
                .build()
                .toUriString();

        return builder.routes()
                .route("patient_service_2", r -> r
                        .method(HttpMethod.GET) // Match GET requests
                        .and()
                        .path("/patients/{id}")
                        .uri(uriPatient)
                )
                .build();
    }

    @Bean
    public RouteLocator gatewayToPatientRoute3(RouteLocatorBuilder builder) {
        String uriPatient = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(patientIp)
                .port(patientPort)
                .build()
                .toUriString();

        return builder.routes()
                .route("patient_service_3", r -> r
                        .method(HttpMethod.PUT)
                        .and()
                        .path("/patients/{id}")
                        .uri(uriPatient)
                )
                .build();
    }

    /**
     * Gateway to Note microservice routes :
     * Redirections vers le microservice diagnosis-notes
     */
    @Value("${NOTE_IP}")
    private String noteIp;

    @Value("${NOTE_PORT}")
    private int notePort;

    @Bean
    public RouteLocator gatewayToNoteRoute1(RouteLocatorBuilder builder) {
        String uriNote = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(noteIp)
                .port(notePort)
                .build()
                .toUriString();

        return builder.routes()
                .route("note_service_1", r -> r
                        .method(HttpMethod.GET)
                        .and()
                        .path("/notes/{id}")
                        .uri(uriNote)
                )
                .build();
    }

    @Bean
    public RouteLocator gatewayToNoteRoute2(RouteLocatorBuilder builder) {
        String uriNote = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(noteIp)
                .port(notePort)
                .build()
                .toUriString();

        return builder.routes()
                .route("note_service_2", r -> r
                        .method(HttpMethod.POST)
                        .and()
                        .path("/notes/{id}")
                        .uri(uriNote)
                )
                .build();
    }

    /**
     * Gateway to Risk microservice routes :
     * Redirections vers le microservice diagnosis-risk
     */
    @Value("${RISK_IP}")
    private String riskIp;

    @Value("${RISK_PORT}")
    private int riskPort;

    @Bean
    public RouteLocator gatewayToRiskRout1(RouteLocatorBuilder builder) {
        String uriRisk = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(riskIp)
                .port(riskPort)
                .build()
                .toUriString();

        return builder.routes()
                .route("risk_service_1", r -> r
                        .method(HttpMethod.GET)
                        .and()
                        .path("/risks/{id}")
                        .uri(uriRisk)
                )
                .build();
    }

}