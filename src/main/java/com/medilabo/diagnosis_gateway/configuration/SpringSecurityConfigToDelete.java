//package com.medilabo.diagnosis_gateway.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig {
//
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////                .authorizeHttpRequests(requests ->
////                        requests
////                                .requestMatchers("/css/**").permitAll()
////                                .requestMatchers("/logoPMB.png").permitAll()
////                                .anyRequest().authenticated()
////                )
////                .formLogin(withDefaults())
////                .authenticationManager(http); // Ajout de l'AuthenticationManage                );
//////                .authenticationProvider(new DaoAuthenticationProvider());
//////                .authenticationProvider(authProvider);
////        return http.build();
////    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(requests ->
//                        requests
//                                .requestMatchers("/css/**").permitAll()
//                                .requestMatchers("/logoPMB.png").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .formLogin(withDefaults())
//                .authenticationManager(authenticationManager(http)); // Ajout de l'AuthenticationManager
//
//        return http.build();
//    }
//
//    private AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .authenticationProvider(authenticationProvider())
//                .build();
//    }
//
//    @Bean
////    public DaoAuthenticationProvider authenticationProvider() {
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService());
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
////    @Bean
////    public AuthenticationProvider customAuthenticationProvider() {
////        return new AuthenticationProvider() {
////            @Override
////            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
////                String username = authentication.getName();
////                String password = authentication.getCredentials().toString();
////
////                if ("user".equals(username) && "password".equals(password)) {
//////                    return new UsernamePasswordAuthenticationToken(username, password, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
////                    return new UsernamePasswordAuthenticationToken(username, password);
////                } else {
////                    return null;
////                }
////            }
////
////            //vérifie si l'implémentation d'AuthenticationProvider prend en charge le type d'objet d'authentification transmis à la méthode . ici, UsernamePasswordAuthenticationToken
////            @Override
////            public boolean supports(Class<?> authentication) {
////                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
////            }
////        };
////    }
//
//    //    @Bean
////    GlobalAuthenticationConfigurerAdapter adapter(InMemoryUserDetailsManager inMemoryUserDetails, PasswordEncoder passwordEncoder) {
////        return new GlobalAuthenticationConfigurerAdapter() {
////            @Override
////            public void configure(AuthenticationManagerBuilder auth) throws Exception {
////                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////                provider.setUserDetailsService(inMemoryUserDetails);
////                provider.setUserDetailsPasswordService(inMemoryUserDetails);
////                provider.setPasswordEncoder(passwordEncoder);
////                auth.authenticationProvider(provider);
////            }
////        };
////    }
//
//
//    @Bean
//    public UserDetailsService userDetailsService() { //procedure pour recuperer données des utilisateurs qui veulent se connecter - ici, in memoriam
//        UserDetails doctorUser = User.builder()
//                .username("house")
//                .password("{bcrypt}$2a$12$Rr1QST6IeWJ0sRXfY9UCSO2GxfzKrW2pkCsJxE8STVjVeGhGX0ybu")   //123
//                .build();
//
//        return new InMemoryUserDetailsManager(doctorUser);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
//
