package com.medilabo.diagnosis_gateway.configuration;

import com.medilabo.diagnosis_gateway.repositories.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Bean userDetailsService sert à implémenter l'interface UserDetailService, utilisée
 dans la procedure d'authentification.
 *
 * @see SecurityConfig2
 */
@Service
public class CustomUserDetailService implements ReactiveUserDetailsService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.justOrEmpty(userCredentialsRepository.findByUsername(username))  // Convert Optional<UserApp> to Mono<UserApp>
                .map(userFound -> User.builder()
                        .username(userFound.getUsername())
                        .password(userFound.getPassword())
                        .roles(userFound.getRole())
                        .build())
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found in DB - Username used: " + username)));
    }

}

