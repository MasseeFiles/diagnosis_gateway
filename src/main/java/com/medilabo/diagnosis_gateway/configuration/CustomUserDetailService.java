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
 * Bean CustomUserDetailService sert à implémenter l'interface ReactiveUserDetailsService. Il est utilisé
 * pour indiquer à Spring Security où récuperer les données des utilisateurs pouvant accès à l'appli
 * (ici, dans une BDD) pour les comparer aux données fournies lors de la procédure d'authentification.
 *
 * @see SpringSecurityConfig
 */
@Service
public class CustomUserDetailService implements ReactiveUserDetailsService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Optional<UserApp> userDetails = userCredentialsRepository.findByUsername(username);
        if (userDetails.isPresent()) {
            return Mono.justOrEmpty(userCredentialsRepository.findByUsername(username))  // Conversion du Optional<UserApp> en Mono<UserApp>
                    .map(userFound -> User.builder()
                            .username(userFound.getUsername())
                            .password(userFound.getPassword())
                            .roles(userFound.getRole())
                            .build());
        } else {
            return Mono.error(new UsernameNotFoundException("Utilisateur non trouvé dans la BDD - Username: " + username));
        }
    }

}

