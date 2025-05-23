package com.daily.expense.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()                   // Disable CSRF
            .authorizeHttpRequests()
            .anyRequest().permitAll()           // Allow all requests
            .and()
            .formLogin().disable()              // Disable form login
            .httpBasic().disable();             // Disable basic auth

        return http.build();
    }
}
