package com.onboard.config;

import com.onboard.auth.AuthExceptionEntryPoint;
import com.onboard.auth.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.lang.reflect.Array;
import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http
                .formLogin().disable()
                .logout().disable()

//                .headers().frameOptions().sameOrigin()
//                .and()

//                .cors().configurationSource(corsConfigSource())
//                .and()
                .cors().disable()

                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/user/**").hasRole(UserRole.USER.getKey())
                .anyRequest().permitAll()
                .and()

                .exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint())
                .and()

//                .addfilterbe
                .build();
    }

    private UrlBasedCorsConfigurationSource corsConfigSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfig.addAllowedOriginPattern("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
