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
 *
 * Les règles d'autorisation sont basées sur l'attribut ROLE du UserApp.
 *
 * Implementation d'un mechanisme d'authentification stateless avec l'utilisation d'un
 * webFilter
 * @see JWTAuthenticationWebFilter
 *
 * Injection d'une classe CustomUserDetailService pour récupérer les données des patients
 * dans une BDD.
 * @see CustomUserDetailService
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

                // Stateless session management par defaut dans une aplli basée sur webflux donc pas besoin de configurer le session management ????????????

                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())  // This disables session management

                .authorizeExchange(exchange -> exchange
                        /**
                         * Regles d'autorisation : basé sur l'attribut Role du UserApp.
                         */
                        .pathMatchers("/view/noteForm/**").hasRole("DOCTOR")
                        .pathMatchers("/view/addNote/**").hasRole("DOCTOR")
                        //pour permettre acces au formulaire d'authentification
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
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

//implementation du mechanisme d'authentification
    @Bean
    public ReactiveAuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        //UserDetailsRepositoryReactiveAuthenticationManager est une implementation de ReactiveAuthenticationManager.
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
