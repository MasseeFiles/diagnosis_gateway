//package com.medilabo.diagnosis_gateway.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.*;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import reactor.core.publisher.Mono;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SpringSecurityConfig {
//
//    @Autowired
//    private ReactiveUserDetailsService userDetailsService;
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//                .authorizeExchange(exchange -> exchange
//                        .anyExchange().authenticated()
//                )
//                .httpBasic(withDefaults())
//                .csrf(ServerHttpSecurity.CsrfSpec::disable);
//
//        return http.build();
//    }
//    /**
//     * Bean userDetailsService sert à spécifier le userDetailService à utiliser pour la procedure d'authentification.
//     *
//     * Interface userDetailService implementée ici dans une classe CustomUserDetailService
//     *
//     *
//     * @see AuthenticationProvider
//     */
////    @Bean
////    public ReactiveUserDetailsService userDetailsService() {
////        return userDetailsService;
////        UserDetails user = User.withUsername("house")
////                .password(passwordEncoder().encode("123"))
////                .roles("USER")
////                .build();
////
////        return username -> Mono.just(user);
//    }
//
//
//    /**
//     * Bean authenticationProvider sert à configurer une partie de la procédure d'AUTHENTIFICATION.
//     *
//     * Implementé par un objet DaoAuthenticationProvider qui permet de définir:
//     * un userDetailsService:
//     *      comment sont récupérées dans l'appli les données à comparer à celle fournies par un utilisateur qui veut s'identifier
//     *
//     *
//     * un passwordEncoder:
//     *      comment est codé le mot de passe d'un utilisateur
//     */
////    @Bean
////    public ReactiveAuthenticationManager  authenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
////        return authentication -> userDetailsService.findByUsername(authentication.getName())
////                .filter(user -> passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()))
////                .map(user -> new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
////
////        UserDetailsRepositoryReactiveAuthenticationManager authManager =
////                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
////        authManager.setPasswordEncoder(passwordEncoder);  // Use password encoder for matching passwords
////        return authManager;
//
//
////        return new ReactiveAuthenticationManager() {
////            @Override
////            public Mono<Authentication> authenticate(Authentication authentication) {
////                return userDetailsService.findByUsername(authentication.getName())
////                        .filter(user -> passwordEncoder().matches(authentication.getCredentials().toString(), user.getPassword()))
////                        .map(user -> new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
////            }
////        };
////    }
//
//    /**
//     * Bean userDetailsService sert à spécifier le userDetailService à utiliser pour la procedure d'authentification.
//     *
//     * Interface userDetailService implementée ici dans une classe CustomUserDetailService
//     *
//     *
//     * @see AuthenticationProvider
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
//
