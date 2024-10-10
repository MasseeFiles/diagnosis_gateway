package com.medilabo.diagnosis_gateway.configuration;


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
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Classe de configuration de la couche Spring Security.
 * Les règles d'autorisation sont basées sur l'attribut ROLE du UserApp (accès complet
 * pour le role DOCTOR).
 * Implementation d'un mechanisme d'authentification stateless avec la methode
 * httpbasic() : renvoie une string encodée (Base64) dans le header Authorization.
 * Injection d'une classe  CustomUserDetailService pour récupérer les données des patients
 * dans une BDD.
 */
@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchange -> exchange
                        /**
                         * Regles d'autorisation : basé sur l'attribut Role du UserApp.
                         */
                        .pathMatchers("/view/listNote/**").hasRole("DOCTOR")
                        .pathMatchers("/view/noteForm/**").hasRole("DOCTOR")
                        .pathMatchers("/view/addNote/**").hasRole("DOCTOR")
                        /**
                         * Regles d'authentification : accès à l'appli
                         */
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(customUserDetailService);
        authManager.setPasswordEncoder(passwordEncoder);
        return authManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
