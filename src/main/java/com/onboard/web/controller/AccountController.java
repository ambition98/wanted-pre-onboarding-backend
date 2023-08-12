package com.onboard.web.controller;

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

import java.security.Principal;

@RestController
@Slf4j
@RequestMapping("/user") // UserRole.USER 을 가진 사용자만 접근 가능
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/check")
    public ResponseEntity<CommonResp> check(Principal principal) {
        return RespBuilder.make(HttpStatus.OK, "Succeed", accountService.getById(principal.getName()));
    }

}
