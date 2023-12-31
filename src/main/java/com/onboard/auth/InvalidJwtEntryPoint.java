package com.onboard.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class InvalidJwtEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse resp,
                         AuthenticationException authException) throws IOException, ServletException {

        log.info("Invalid JWT");
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter writer =  resp.getWriter();
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        String response = "{\"httpStatus\": \"" + HttpStatus.UNAUTHORIZED + "\", \"msg\": \"로그인이 필요합니다.\"}";

        writer.print(response);
        writer.flush();
        writer.close();
    }
}
