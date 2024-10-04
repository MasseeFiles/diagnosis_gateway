package com.medilabo.diagnosis_gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public WebClientConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .filter(jwtRequestFilter())  // Adding the JWT filter
                .build();
    }

    private ExchangeFilterFunction jwtRequestFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest ->
                jwtTokenProvider.getToken()
                        .flatMap(token -> {
                            ClientRequest newRequest = ClientRequest.from(clientRequest)
                                    .header("Authorization", "Bearer " + token)
                                    .build();
                            return Mono.just(newRequest);
                        })
        );
    }

}
