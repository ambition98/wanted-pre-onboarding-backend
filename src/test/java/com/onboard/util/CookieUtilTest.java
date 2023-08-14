package com.onboard.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CookieUtilTest {

    @InjectMocks CookieUtil cookieUtil;

    private final String NAME = "cookieName";
    private final String VALUE = "cookieValue";

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void setCookie() {
        //given
        MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        cookieUtil.setCookie(response, NAME, VALUE);

        //then
        assertThat(Objects.requireNonNull(response.getCookie(NAME))
                .getValue()).isEqualTo(VALUE);
    }

    @Test
    void deleteCookie() {
        //given
        MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        cookieUtil.deleteCookie(response, NAME);
        //then
        assertThat(Objects.requireNonNull(response.getCookie(NAME))
                .getValue()).isNull();
    }

    @Test
    void getCookie() {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(new Cookie(NAME, VALUE));

        //when
        Cookie resultCookie = cookieUtil.getCookie(request, NAME);

        //then
        assertThat(resultCookie.getName()).isEqualTo(NAME);
        assertThat(resultCookie.getValue()).isEqualTo(VALUE);
    }

    @Test
    void setTokenToCookie() {
        //given
        MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        cookieUtil.setTokenToCookie(response, VALUE);

        //then
        assertThat(Objects.requireNonNull(response.getCookie("tk"))
                .getValue()).isEqualTo(VALUE);
    }

    @Test
    void getTokenFromCookie() {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(new Cookie("tk", VALUE));

        //when
        String tokenValue = cookieUtil.getTokenFromCookie(request);

        //then
        assertThat(tokenValue).isEqualTo(VALUE);
    }
}