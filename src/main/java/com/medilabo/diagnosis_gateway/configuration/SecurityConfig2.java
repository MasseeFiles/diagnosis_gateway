package com.medilabo.diagnosis_gateway.configuration;


import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.SecretKey;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig2 {

    @Autowired
    private CustomUserDetailService customUserDetailService;  // Inject the custom user service

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchange -> exchange
                        /**
                         * Regles d'authentification : accès à l'appli
                         */
                        .anyExchange().authenticated()
                        /**
                         * Regles d'authorisation : basé sur l'attribut Role du UserApp.
                         */
                        .pathMatchers("/view/listNote/**").hasRole("DOCTOR")
                        .pathMatchers("/view/noteForm/**").hasRole("DOCTOR")
                        .pathMatchers("/view/addNote/**").hasRole("DOCTOR")

                )
                .httpBasic(withDefaults())
//                //utilisation de jwt token pour authentification
                .oauth2ResourceServer((oauth2) -> oauth2
                //   default configuration" for JWT processing.

                        .jwt(Customizer.withDefaults())
//                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder))

                )

                .formLogin(withDefaults())


                .csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(customUserDetailService);  // Use your custom service
        authManager.setPasswordEncoder(passwordEncoder);  // Set the password encoder
        return authManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
