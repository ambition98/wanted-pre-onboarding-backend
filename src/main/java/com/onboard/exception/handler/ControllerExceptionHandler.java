package com.onboard.exception.handler;

import com.onboard.exception.IncorrectLoginInfo;
import com.onboard.exception.UnauthorizedException;
import com.onboard.web.model.Resp.CommonResp;
import com.onboard.web.model.Resp.RespBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = "com.onboard.web.controller")
public class ControllerExceptionHandler {

    //로그인 실패
    @ExceptionHandler(IncorrectLoginInfo.class)
    public ResponseEntity<CommonResp> incorrectLoginInfoHandler(IncorrectLoginInfo e) {
        return RespBuilder.make(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    //존재하지 않는 리소스
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonResp> entityNotFoundExceptionHandler(EntityNotFoundException e) {
        return RespBuilder.make(HttpStatus.NOT_FOUND, e.getMessage());
    }

    //지원하지 않는 Http method
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CommonResp> httpRequestMethodNotSupportedExceptionHandler() {
        return RespBuilder.make(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 Http method 입니다.");
    }

    //리소스에 대한 권한이 없음
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonResp> unauthorizedExceptionHandler(UnauthorizedException e) {
        return RespBuilder.make(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    //API 요청 파라미터 값의 유효성 위반
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResp> invalidArgumentExceptionHandler(MethodArgumentNotValidException e) {
        String msg = e.getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return RespBuilder.make(HttpStatus.BAD_REQUEST, msg);
    }

    //알 수 없는 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResp> unknownExceptionHandler(Exception e) {
        log.warn("[Unexpected Error] " + e.getMessage());
        return RespBuilder.make(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
    }
}
