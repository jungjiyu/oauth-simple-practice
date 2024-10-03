package com.example.oauth2_01.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.web.servlet.function.RequestPredicates.headers;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((auth) -> auth
                        .anyRequest().permitAll())

                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()));  // h2-db 접근 가능하게 하기 위함

        return http.build();

    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder() { // UserService 같은 곳에서 PasswordEncoder 빈 자동 주입 가능하게 하기 위해 빈 등록 필요
        return new BCryptPasswordEncoder();
    }
}
