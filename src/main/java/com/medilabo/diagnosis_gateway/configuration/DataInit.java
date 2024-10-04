package com.medilabo.diagnosis_gateway.configuration;

import com.medilabo.diagnosis_gateway.model.UserApp;
import com.medilabo.diagnosis_gateway.repositories.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInit {

    @Autowired
    UserCredentialsRepository userCredentialsRepository;

    @Bean
    public DataInit init() {

        UserApp[] userApps = new UserApp[2];
        userApps[0] = new UserApp(1L, "doctor", "$2a$12$WWF96NoaGZb46WBZo.xDQuu83YU3P8T3Yu5Qcb.mFL6lYFCfzNTIe", "DOCTOR");
        userApps[1] = new UserApp(2L, "assistant", "$2a$12$WWF96NoaGZb46WBZo.xDQuu83YU3P8T3Yu5Qcb.mFL6lYFCfzNTIe", "ASSISTANT");

        userCredentialsRepository.saveAll(Arrays.asList(userApps));

        return new DataInit();
    }

}

