package com.onboard.web.controller;

import com.onboard.web.model.Req.Signin;
import com.onboard.web.model.Req.Signup;
import com.onboard.web.model.Resp.CommonResp;
import com.onboard.web.model.Resp.RespBuilder;
import com.onboard.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResp> signup(@RequestBody @Valid Signup signup) {
        return RespBuilder.make(HttpStatus.OK, "Succeed", authService.signup(signup));
    }

    @PostMapping("/signin")
    public ResponseEntity<CommonResp> signin(@RequestBody @Valid Signin signin, HttpServletResponse resp) {
        return RespBuilder.make(HttpStatus.OK, "Succeed", authService.signin(signin, resp));
    }

}
