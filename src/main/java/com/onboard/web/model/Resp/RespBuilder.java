package com.onboard.web.model.Resp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// ResponseEntity를 편하게 만들기 위한 클래스
public class RespBuilder {
    public static ResponseEntity<CommonResp> make(HttpStatus status, String msg) {
        return ResponseEntity.status(status).body(new CommonResp(status, msg));
    }

    public static ResponseEntity<CommonResp> make(HttpStatus status, String msg, Object dto) {
        return ResponseEntity.status(status).body(new CommonResp(status, msg, dto));
    }
}
