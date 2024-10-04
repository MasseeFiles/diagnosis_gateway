package com.medilabo.diagnosis_gateway.repositories;

import com.medilabo.diagnosis_gateway.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserApp, Long>, JpaSpecificationExecutor<UserApp> {
    Optional<UserApp> findByUsername(String username);

}
