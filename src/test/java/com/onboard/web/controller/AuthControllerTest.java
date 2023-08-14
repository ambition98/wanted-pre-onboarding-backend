package com.onboard.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onboard.filter.JwtAuthFilter;
import com.onboard.util.CookieUtil;
import com.onboard.util.JwtUtil;
import com.onboard.web.model.Req.Signin;
import com.onboard.web.model.Req.Signup;
import com.onboard.web.service.AuthService;
import com.onboard.web.service.PostService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private CookieUtil cookieUtil;

    private final Gson gson = new GsonBuilder().create();

    @Test
    void 회원가입() throws Exception {
        //given
        System.out.println(MediaType.APPLICATION_JSON.getType());
        Signup signup = new Signup();
        signup.setEmail("test@wanted.co.kr");
        signup.setPassword("password");
        String body = gson.toJson(signup);

        //when
        //then
        mockMvc.perform(post("/signup")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 로그인() throws Exception {
        //given
        Signin signin = new Signin();
        signin.setEmail("test@wanted.co.kr");
        signin.setPassword("password");
        String body = gson.toJson(signin);

        //when
        //then
        mockMvc.perform(post("/signin")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 유효하지_않은_파라미터() throws Exception {
        //given
        Signin signin = new Signin();
        signin.setEmail("wrongEmail");
        signin.setPassword("wrongPw");
        String body = gson.toJson(signin);

        //when
        //then
        mockMvc.perform(post("/signup")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/signin")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}