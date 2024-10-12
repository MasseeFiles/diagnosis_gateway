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
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

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

//    @Autowired
//    private CustomAuthenticationConverter customAuthenticationConverter;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    @Autowired
    JWTAuthenticationWebFilter jwtAuthenticationWebFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                //csrf desactivé car basé sur des sessions.
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())  // This disables session management

                .authorizeExchange(exchange -> exchange
                        /**
                         * Regles d'autorisation : basé sur l'attribut Role du UserApp.
                         */
//                        .pathMatchers("/view/listNote/**").hasRole("DOCTOR")
//                        .pathMatchers("/view/noteForm/**").hasRole("DOCTOR")
//                        .pathMatchers("/view/addNote/**").hasRole("DOCTOR")

                        .pathMatchers("/login", "/favicon.ico").permitAll()

                        /**
                         * Regles d'authentification : accès à l'appli
                         */
                        .anyExchange().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .authenticationSuccessHandler(jwtAuthenticationSuccessHandler)
//                        .defaultSuccessUrl("/home", true) // Redirect to this URL after successful login

                )

//        Spring Security processes filters in the order they are defined in the filter chain.
//        By placing the JWT filter after form login, you ensure that form login processes the
//        initial authentication. After that, the JWT filter kicks in for all future requests
//        with the token.
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);  // Add JWT filter

        return http.build();
    }

//                // Stateless session management par defaut dans une aplli basée sur webflux donc pas besoin de configurer le session management
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )



    @Bean
    public ReactiveAuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        //recuperation des infos d'un user en fonction de son username
        UserDetailsRepositoryReactiveAuthenticationManager authManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(customUserDetailService);
        //overide methode pour recherche dans le body de username


        authManager.setPasswordEncoder(passwordEncoder);
        return authManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
