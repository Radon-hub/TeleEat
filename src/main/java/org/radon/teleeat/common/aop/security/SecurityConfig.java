package org.radon.teleeat.common.aop.security;


import lombok.extern.slf4j.Slf4j;
import org.radon.teleeat.auth.application.port.in.AddSupervisorUseCase;
import org.radon.teleeat.auth.application.port.out.AuthRepository;
import org.radon.teleeat.common.aop.errorHandling.RestAccessDeniedHandler;
import org.radon.teleeat.common.aop.errorHandling.RestAuthenticationEntryPoint;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.UUID;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthRepository authRepository;
    private final JwtAuthFilter jwtAuthFilter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(AuthRepository authRepository, JwtAuthFilter jwtAuthFilter, RestAuthenticationEntryPoint restAuthenticationEntryPoint, RestAccessDeniedHandler restAccessDeniedHandler, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.jwtAuthFilter = jwtAuthFilter;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        String username = "supervisor";
        String rawPassword = UUID.randomUUID().toString().substring(0, 12);

        log.warn("\n\n*** SUPERVISOR CREDENTIALS *** \n\n username: {} \n\n password: {} \n\n\n",
                username, rawPassword);

        UserDetails supervisor = User.builder()
                .username(username)
                .password(encoder.encode(rawPassword))
                .roles("SUPERVISOR")
                .build();

        SupervisorPasswordHolder.setPassword(rawPassword);

        return new InMemoryUserDetailsManager(supervisor);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain publicChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/public/**", "/public", "/auth/**", "/auth","/api/v1/order/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth

                        // food routes just ADMIN or SUPERVISOR
                        .requestMatchers("/api/v1/foods/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR")

                        // user routes ONLY SUPERVISOR
                        .requestMatchers("/auth/admin/signup")
                        .hasRole("SUPERVISOR")

                        // everything else under /api/**
                        .anyRequest()
                        .authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(authRepository);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration  authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}
