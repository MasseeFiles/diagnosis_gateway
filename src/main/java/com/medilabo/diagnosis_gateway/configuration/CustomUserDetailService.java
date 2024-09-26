package com.medilabo.diagnosis_gateway.configuration;

import com.medilabo.diagnosis_gateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailService implements ReactiveUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.justOrEmpty(userRepository.findByUsername(username))  // Convert Optional<UserApp> to Mono<UserApp>
                .map(userFound -> User.builder()
                        .username(userFound.getUsername())
                        .password(userFound.getPassword())
                        .roles(userFound.getRole())
                        .build())
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found in DB - Username used: " + username)));
    }

}

