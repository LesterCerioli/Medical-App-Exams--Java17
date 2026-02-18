package com.medical.app.exams.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html"
                ).permitAll()
                
                .requestMatchers("/auth/token", "/auth/validate").permitAll()
                
                .anyRequest().authenticated()
            )
            
            .csrf(csrf -> csrf.disable())
            
            .formLogin(formLogin -> formLogin.disable())
            
            .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}