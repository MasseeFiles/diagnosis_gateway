package com.medilabo.diagnosis_gateway.configuration;

import com.medilabo.diagnosis_gateway.model.UserApp;
import com.medilabo.diagnosis_gateway.repositories.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Bean CustomUserDetailService sert à implémenter l'interface ReactiveUserDetailsService. Utilisé
 * pour indiquer à Spring Security où récuperer les données des utilisateurs pouvant accès à l'appli
 * (ici, dans une BDD) pour les comparer aux données fournies lors de la procédure d'authenthification.
 *
 * @see SpringSecurityConfig
 */
@Service
public class CustomUserDetailService implements ReactiveUserDetailsService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        System.out.println(username);
        Optional<UserApp> userDetails = userCredentialsRepository.findByUsername(username);
        if (userDetails.isPresent()) {
            //a enlever
            System.out.println(userDetails.toString());

            return Mono.justOrEmpty(userCredentialsRepository.findByUsername(username))  // Convert Optional<UserApp> to Mono<UserApp>
                    .map(userFound -> User.builder()
                            .username(userFound.getUsername())
                            .password(userFound.getPassword())
                            .roles(userFound.getRole())
                            .build());
//                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found in DB - Username used: " + username)));
        } else {
            return Mono.error(new UsernameNotFoundException("User not found in DB - Username used: " + username));
        }
    }
}

