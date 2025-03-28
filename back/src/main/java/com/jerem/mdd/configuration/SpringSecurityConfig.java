package com.jerem.mdd.configuration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.jerem.mdd.service.JwtTokenProvider;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableWebSecurity
@Slf4j
public class SpringSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String[] AUTH_WHITELIST =
            {"/api/auth/login", "/api/auth/register", "/swagger-ui/**", "/v3/api-docs/**"

            };

    public SpringSecurityConfig(UserDetailsService userDetailsService,
            JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Configures the {@link SecurityFilterChain} bean, overiding default security rules applied to
     * HTTP requests.
     * <p>
     * This configuration: - Disables CSRF protection (may need to be adjusted for specific use
     * cases). - Permits access to the {@link AUTH_WHITELIST} URL without authentication. - Requires
     * authentication for all other requests. - Configures Spring Security to act as an OAuth
     * Ressource Server and enforce jwt authentication
     * </p>
     * 
     * @param http the {@link HttpSecurity} instance used to configure HTTP security
     * @return the configured {@link SecurityFilterChain} bean
     * @throws Exception if there is a configuration error
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(withDefaults())).build();
    }


    /**
     * This bean is requierd by Spring security and provides a {@link PasswordEncoder} to use BCrypt
     * for password encoding.
     * <p>
     * BCrypt is a secure hashing algorithm used to protect user passwords in the database.
     * </p>
     * 
     * @return the {@link PasswordEncoder} bean configured with BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Get a JwtDecoder from the factory. This bean is requierd by Spring security.
     * 
     * @return the configured {@link JwtDecoder} bean
     * 
     */
    @Bean
    public JwtDecoder JwtDecoder() {
        return this.jwtTokenProvider.createJwtDecoder();
    }

    /**
     * Get a JwtEncoder from the factory. This bean is requierd by Spring security.
     * 
     * @return the configured {@link JwtEncoder} bean
     * 
     */
    @Bean
    public JwtEncoder JwtEncoder() {
        return this.jwtTokenProvider.createJwtEncoder();
    }

    /**
     * Configures the Spring Security {@link AuthenticationManager} bean, which is responsible for
     * authenticating users. This bean is requierd by Spring security.
     * <p>
     * </p>
     * 
     * @param http the {@link HttpSecurity} instance, required for configuring authentication.
     * @param passwordEncoder the {@link PasswordEncoder} used to check passwords.
     * @return the configured {@link AuthenticationManager} bean
     * @throws Exception if there is a configuration error
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
            PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }


}
