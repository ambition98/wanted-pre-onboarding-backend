package com.onboard.web.model.Resp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CommonResp {

    public CommonResp(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public CommonResp(HttpStatus status, String msg, Object dto) {
        this.status = status;
        this.msg = msg;
        this.dto = dto;
    }

    private HttpStatus status;
    private String msg;
    private Object dto;
}
