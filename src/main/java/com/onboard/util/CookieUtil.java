package com.onboard.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class CookieUtil {
    public void setCookie(HttpServletResponse resp, String key, String value) {
        resp.addCookie(makeCookie(key, value));
    }

    public void deleteCookie(HttpServletResponse resp, String key) {
        resp.addCookie(makeCookie(key, null));
    }

    // 중복 이름을 가진 쿠키에 대한 처리는 적용하지 않음
    public Cookie getCookie(HttpServletRequest req, String key) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null || cookies.length < 1) {
            return null;
        }

        Cookie[] res = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(key))
                .toArray(Cookie[]::new);

        if (res.length > 0) return res[0];
        else return null;
    }

    private Cookie makeCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
