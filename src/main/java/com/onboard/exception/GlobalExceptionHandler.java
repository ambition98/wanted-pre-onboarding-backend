package com.onboard.exception;

import com.onboard.web.model.Resp.CommonResp;
import com.onboard.web.model.Resp.RespBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = "com.onboard.web.controller")
public class GlobalExceptionHandler {

    // API 요청 파라미터 값의 유효성 위반
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResp> inValidArgumentException(MethodArgumentNotValidException e) {
        String msg = e.getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return RespBuilder.make(HttpStatus.BAD_REQUEST, msg);
    }

    // 알 수 없는 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResp> unknownExceptionHandler(Exception e) {
        log.warn("[FATAL ERROR] " + e.getMessage());
        return RespBuilder.make(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
    }
}
