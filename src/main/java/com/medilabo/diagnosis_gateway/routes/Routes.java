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
    //private String viewIp = "localHost";

    @Value("${VIEW_PORT}")
    private int viewPort;
    //private int viewPort = 8082;

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


}

