package com.jerem.mdd.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.jerem.mdd.configuration.properties.AppConfigProperties;
import com.jerem.mdd.repository.UserRepository;
import com.jerem.mdd.service.DefaultJwtTokenProvider;
import com.jerem.mdd.service.DefaultUserManagementService;
import com.jerem.mdd.service.JwtTokenProvider;
import com.jerem.mdd.service.UserManagementService;

/**
 * Configuration class for the application. This class defines beans that will be managed by the
 * Spring application.
 */
@Configuration
public class AppConfig {

    /**
     * Defines a JwtTokenProvider bean for handling JSON Web Token (JWT) creation and validation.
     * Here we uses an HMAC-based JWT factory configured with properties from AppConfigProperties.
     *
     * @param appConfigProperties Configuration properties
     * @return a JwtFactory specific implementation
     */
    @Bean
    public JwtTokenProvider jwtFactory(AppConfigProperties appConfigProperties) {
        return new DefaultJwtTokenProvider(appConfigProperties.getJwtsecretkey());
    }

    /**
     * Defines a UserManagementService bean that handles user management operations. Here we uses a
     * UserManagementService implementation : DefaultUserManagementService.
     *
     * @param userRepository Repository for accessing user data.
     * @param passwordEncoder Encoder for hashing passwords.
     * @return a UserManagementService specific implementation
     */
    @Bean
    public UserManagementService userManagementService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return new DefaultUserManagementService(userRepository, passwordEncoder);
    }

}
