package com.jerem.mdd.configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerem.mdd.model.UserEntity;
import com.jerem.mdd.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("init sample data");
        if (userRepository.count() == 0) {
            List<UserEntity> sampleUsers = loadUsersFromFile("data/users.json");
            userRepository.saveAll(sampleUsers);
            log.debug("successfully added " + sampleUsers.size() + " sample users");
        }
    }

    private List<UserEntity> loadUsersFromFile(String filePath)
            throws StreamReadException, DatabindException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource(filePath);
        List<UserEntity> users = objectMapper.readValue(resource.getInputStream(),
                new TypeReference<List<UserEntity>>() {});

        users.forEach((user) -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        });

        return users;

    }


}
