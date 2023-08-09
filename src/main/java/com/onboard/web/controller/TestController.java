package com.onboard.web.controller;

import com.onboard.web.entity.AccountEntity;
import com.onboard.web.model.Req.SignupReq;
import com.onboard.web.model.Resp.CommonResp;
import com.onboard.web.model.Resp.RespBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public String test() {
        AccountEntity entity1 = new AccountEntity();
        AccountEntity entity2 = new AccountEntity();
        System.out.println(entity1.getId());
        System.out.println(entity2.getId());
        if (entity1 == entity2) {
            log.info("entity1 == entity2");
        } else {
            log.info("entity1 != entity2");
        }

        if (entity1.equals(entity2)) {
            log.info("entity1 and entity2 is equals");
        } else {
            log.info("entity1 and entity2 is not equals");
        }

        return "test";
    }

    @GetMapping("/test2")
    public ResponseEntity signupTest(@Valid SignupReq signupReq, Errors errors) {
        log.info("/test2");
        if (errors.hasErrors()) {
            log.info("error");
            return ResponseEntity.badRequest().body("error");
        }
        log.info("ok");
        return ResponseEntity.ok(signupReq);
    }

    @PostMapping("/post")
    public ResponseEntity<CommonResp> postTest(@RequestBody @Valid SignupReq signupReq) {
//        if (errors.hasErrors()) {
//            errors.getAllErrors()
//        }
//        log.info(body);
        log.info(signupReq.getEmail());
        log.info(signupReq.getPassword());
        return RespBuilder.make(HttpStatus.OK, "ok", signupReq);
    }
}
