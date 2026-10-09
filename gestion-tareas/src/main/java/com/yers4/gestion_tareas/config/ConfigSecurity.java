package com.yers4.gestion_tareas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yers4.gestion_tareas.security.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@Configuration 
@RequiredArgsConstructor 
public class ConfigSecurity {

    private final JwtAuthentication jwtAuthentication;

    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity){
        
        httpSecurity
        .csrf(csrf -> csrf.disable())

        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/Auth/registre").permitAll()
                .requestMatchers("/tareas/**").authenticated()
                .anyRequest().authenticated()

            ).addFilterBefore(jwtAuthentication,UsernamePasswordAuthenticationFilter.class);

            return httpSecurity.build();
        
    }
    
}
