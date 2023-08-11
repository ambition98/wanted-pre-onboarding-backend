package com.onboard.web.model.Req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SigninReq {
    @NotEmpty
    @Pattern(regexp = "^.+@.+$", message = "잘못된 형식입니다.")
    private String email;

    @NotEmpty
    @Size(min = 8, message = "8자 이상이어야 합니다.")
    private String password;
}
