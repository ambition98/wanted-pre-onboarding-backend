package com.onboard.web.controller;

import com.onboard.util.JwtUtil;
import com.onboard.web.model.Resp.CommonResp;
import com.onboard.web.model.Resp.RespBuilder;
import com.onboard.web.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@Slf4j
@RequestMapping("/user") // UserRole.USER 을 가진 사용자만 접근 가능
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    @GetMapping("/test")
    public ResponseEntity<CommonResp> test(Principal principal) {
        log.info("/user/test");
        return RespBuilder.make(HttpStatus.OK, "Succeed", accountService.getById(principal.getName()));
    }

}
