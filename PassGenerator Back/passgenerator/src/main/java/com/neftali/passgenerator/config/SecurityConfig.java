package com.neftali.passgenerator.config;

import com.neftali.passgenerator.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable
                )
                /**
                 .cors(cors -> cors.configurationSource(request -> {
                 CorsConfiguration config = new CorsConfiguration();
                 config.setAllowedOrigins(List.of("http://localhost:4200", "https://passgenerator-six.vercel.app"));
                 config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                 config.setExposedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "Origin", "Accept", "Access-Control-Allow-Request-Method"));
                 config.setAllowCredentials(true);
                 return config;
                 }))
                 */
                .authorizeHttpRequests(authRequest ->
                        authRequest
                            .requestMatchers("/auth/**",
                                    "/swagger-ui/**",
                                    "/v3/**",
                                    "/resources/**",
                                    "/health"
                            ).permitAll()
                            .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
