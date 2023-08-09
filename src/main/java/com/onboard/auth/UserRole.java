package com.onboard.auth;

import lombok.Getter;

@Getter
public enum UserRole {
    GUEST("ANONYMOUS", "비로그인 사용자"),
    USER("USER", "로그인 사용자");

    private final String key;
    private final String desc;

    UserRole(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
