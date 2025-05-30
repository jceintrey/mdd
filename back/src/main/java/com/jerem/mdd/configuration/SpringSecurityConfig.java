package com.jerem.mdd.configuration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.jerem.mdd.configuration.filters.JwtDebugFilter;
import com.jerem.mdd.service.JwtTokenProvider;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring Security configuration class for the application.
 *
 * This class sets up HTTP security, authentication mechanisms, and JWT
 * integration to secure the
 * API. It leverages stateless session management and customizes the
 * {@link SecurityFilterChain} to
 * define public and protected endpoints.
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SpringSecurityConfig {
        private final UserDetailsService userDetailsService;
        private final JwtTokenProvider jwtTokenProvider;

        private static final String[] AUTH_WHITELIST = { "/api/auth/login", "/api/auth/register", "/swagger-ui/**",
                        "/v3/api-docs/**"

        };

        public SpringSecurityConfig(UserDetailsService userDetailsService,
                        JwtTokenProvider jwtTokenProvider) {
                this.userDetailsService = userDetailsService;
                this.jwtTokenProvider = jwtTokenProvider;
        }

        /**
         * Configures the {@link SecurityFilterChain} bean, overiding default security
         * rules applied
         * to HTTP requests.
         * <p>
         * This configuration: - Disables CSRF protection (may need to be adjusted for
         * specific use
         * cases). - Permits access to the {@link AUTH_WHITELIST} URL without
         * authentication. -
         * Requires authentication for all other requests. - Configures Spring Security
         * to act as an
         * OAuth Ressource Server and enforce jwt authentication
         * </p>
         * 
         * @param http the {@link HttpSecurity} instance used to configure HTTP security
         * @return the configured {@link SecurityFilterChain} bean
         * @throws Exception if there is a configuration error
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http
                                .cors(Customizer.withDefaults())
                                .csrf(AbstractHttpConfigurer::disable)
                                .sessionManagement(
                                                session -> session
                                                                .sessionCreationPolicy(
                                                                                SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth.requestMatchers(AUTH_WHITELIST)
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(new JwtDebugFilter(),
                                                UsernamePasswordAuthenticationFilter.class)
                                .oauth2ResourceServer((oauth2) -> oauth2.jwt(withDefaults()))
                                .build();
        }

        /**
         * This bean is requierd by Spring security and provides a
         * {@link PasswordEncoder} to use
         * BCrypt for password encoding.
         * <p>
         * BCrypt is a secure hashing algorithm used to protect user passwords in the
         * database.
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
         * Configures the Spring Security {@link AuthenticationManager} bean, which is
         * responsible
         * for authenticating users. This bean is requierd by Spring security.
         * <p>
         * </p>
         * 
         * @param http            the {@link HttpSecurity} instance, required for
         *                        configuring authentication.
         * @param passwordEncoder the {@link PasswordEncoder} used to check passwords.
         * @return the configured {@link AuthenticationManager} bean
         * @throws Exception if there is a configuration error
         */
        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http,
                        PasswordEncoder passwordEncoder) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder.userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder);
                return authenticationManagerBuilder.build();
        }

        /**
         * Configures global CORS settings to allow frontend requests from
         * localhost:4200.
         *
         * <p>
         * Allows all paths (/**) to accept cross-origin requests from the frontend dev
         * server with
         * HTTP methods GET, POST, PUT, DELETE.
         * </p>
         *
         * @return a WebMvcConfigurer bean for CORS configuration
         */
        @Bean
        public WebMvcConfigurer corsConfigurer() {
                return new WebMvcConfigurer() {
                        @SuppressWarnings("null")
                        @Override
                        public void addCorsMappings(CorsRegistry registry) {
                                registry.addMapping("/**")
                                                // here *:80 is used for compose stack configuration and *:4200 for
                                                // local dev configuration
                                                .allowedOrigins("http://localhost:80", "http://127.0.0.1:80",
                                                                "http://localhost:4200", "http://127.0.0.1:4200")
                                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                                .allowedHeaders("*")
                                                .allowCredentials(true); // si tu utilises des cookies / auth header
                        }
                };
        }

}
