package com.onboard.util;

import com.onboard.auth.JwtState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @InjectMocks JwtUtil jwtUtil;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void makeUserToken() {
        String tk = jwtUtil.makeUserToken("id");
        assertThat(tk).isNotBlank();
    }

    @Test
    void makeAuthentication() {
        //given
        String tk = jwtUtil.makeUserToken("id");
        String wrongTk = "wrongTk";

        //when
        Authentication authentication = jwtUtil.makeAuthentication(tk);
        Authentication anonymous = jwtUtil.makeAuthentication(wrongTk);

        //then
        assertThat(authentication.getPrincipal()).isEqualTo("id");
        assertThat(authentication.getCredentials()).isEqualTo(tk);
        assertThat(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")))
                .isEqualTo(true);
        assertThat(authentication.isAuthenticated()).isEqualTo(true);

        assertThat(anonymous.getPrincipal()).isEqualTo("anonymousUser");
        assertThat(anonymous.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))
                .isEqualTo(true);
        assertThat(anonymous.isAuthenticated()).isEqualTo(true);
    }

    @Test
    void getId() {
        //given
        String tk = jwtUtil.makeUserToken("id");

        //when
        String id = jwtUtil.getId(tk);

        //then
        assertThat(id).isEqualTo("id");
    }

    @Test
    void getState() {
        //given
        String tk = jwtUtil.makeUserToken("id");

        //when
        JwtState jwtState = jwtUtil.getState(tk);

        //then
        assertThat(jwtState).isEqualTo(JwtState.OK);
    }
}