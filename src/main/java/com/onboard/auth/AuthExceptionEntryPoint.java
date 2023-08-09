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
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse resp,
                         AuthenticationException authException) throws IOException, ServletException {

        log.info("Invalid JWT");
        PrintWriter writer =  resp.getWriter();
        resp.setHeader("Content-Type", "application/json");
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        String response = "{httpStatus: " + HttpStatus.BAD_REQUEST + ", msg: Need Login}";

        writer.print(response);
        writer.flush();
        writer.close();


    }
}
