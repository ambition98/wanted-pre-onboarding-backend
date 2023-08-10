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
public class HasNoAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse resp,
                         AuthenticationException authException) throws IOException, ServletException {

        log.info("Has no authorization");
        PrintWriter writer =  resp.getWriter();
        resp.setHeader("Content-Type", "application/json");
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        String response = "{httpStatus: " + HttpStatus.UNAUTHORIZED + ", msg: 권한이 없습니다.}";

        writer.print(response);
        writer.flush();
        writer.close();
    }
}
