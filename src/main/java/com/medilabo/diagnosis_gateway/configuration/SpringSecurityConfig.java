package com.medilabo.diagnosis_gateway.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Classe de configuration de la couche Spring Security.
 * <p>
 * Implementation d'un mechanisme d'authentification stateless avec l'utilisation d'un
 * webFilter
 * @see JWTAuthenticationWebFilter
 * <p>
 * Injection d'une classe CustomUserDetailService pour récupérer les données des patients
 * dans une BDD.
 * @see CustomUserDetailService
 * <p>
 * Injection d'une classe JWTAuthenticationSuccessHandler pour la gestion d'une
 * authentification réussie.
 * @see JWTAuthenticationSuccessHandler
 *
 * Les règles d'autorisation sont basées sur l'attribut ROLE du UserApp.
 */
@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    @Autowired
    private JWTAuthenticationWebFilter jwtAuthenticationWebFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        /**
                         * Regles d'autorisation : basé sur l'attribut Role du UserApp.
                         */
                        .pathMatchers("/view/noteForm/**").hasRole("DOCTOR")
                        .pathMatchers("/view/addNote/**").hasRole("DOCTOR")
                        //Accès au formulaire d'authentification
                        .pathMatchers("/login", "/favicon.ico").permitAll()

                        /**
                         * Regles d'authentification : accès à l'appli
                         */
                        .anyExchange().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .authenticationSuccessHandler(jwtAuthenticationSuccessHandler)
                )
                // Le jwtAuthenticationWebFilter remplace le filter par defaut de Spring Security (UsernamePasswordAuthenticationFilter)
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    // Implementation du mechanisme d'authentification (ReactiveAuthenticationManager) par un UserDetailsRepositoryReactiveAuthenticationManager
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
