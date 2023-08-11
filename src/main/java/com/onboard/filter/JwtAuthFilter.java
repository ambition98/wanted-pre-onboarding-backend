package com.onboard.filter;

import com.onboard.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = jwtUtil.getTokenFromCookie(req);
        log.info("jwt: " + jwt);

        // JWT 상태에 따라 OnboardingAuthentication 이나 AnonymousAuthentication 발급
        Authentication authentication = jwtUtil.makeAuthentication(jwt);
        log.info("auth: " + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(req, resp);
    }
}
