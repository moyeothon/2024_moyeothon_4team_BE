package org.fourkibiki.tomorrowletter.moyeothon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())  // CSRF 보호 비활성화
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/h2-console/**", "/letters/**").permitAll()  // 특정 경로에 대한 인증 비활성화
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}