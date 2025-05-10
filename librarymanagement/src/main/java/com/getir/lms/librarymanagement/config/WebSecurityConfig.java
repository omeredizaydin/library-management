package com.getir.lms.librarymanagement.config;

import com.getir.lms.librarymanagement.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            // public endpoints
            .requestMatchers("/api/v1/auth/register/**").permitAll()
            .requestMatchers("/api/v1/auth/authenticate/**").permitAll()
            // private endpoints
            .requestMatchers(HttpMethod.PUT, "/api/v1/auth/**").hasAuthority(Role.LIBRARIAN.name())
            .requestMatchers(HttpMethod.DELETE, "/api/v1/auth/**").hasAuthority(Role.LIBRARIAN.name())
            .requestMatchers("/api/v1/auth/details/**").hasAuthority(Role.LIBRARIAN.name())

            .anyRequest()
            .authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


    return http.build();

  }

}
