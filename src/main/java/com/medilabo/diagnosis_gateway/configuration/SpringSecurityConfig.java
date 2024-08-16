package com.medilabo.diagnosis_gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/css/**", "/logoPMB.png").permitAll() // Autorise l'accès sans authentification
                                .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
//                                .anyRequest().permitAll()
                )
                .formLogin(withDefaults()
                );

//                .formLogin((form) -> form
//                        .loginPage("/login")
////                        .usernameParameter("username") //definit dans le form la valeur considérée par spring comme un username
////                        .passwordParameter("password")
////                        .defaultSuccessUrl("/transfer") // definit la page à afficher si authentification ok
//                                .successHandler(customAuthenticationSuccessHandler())
//
//                                .permitAll()
//                );






//                .logout(LogoutConfigurer::permitAll // Permet de se déconnecter
//                );
//                .csrf(csrf -> csrf.disable()); // Désactive la protection CSRF (à ne faire que si nécessaire)

        return http.build();
    }

//    @Bean
//    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("house")
                .password(passwordEncoder().encode("123")) // Encodage du mot de passe
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
