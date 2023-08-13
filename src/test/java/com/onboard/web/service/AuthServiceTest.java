package com.onboard.web.service;

import com.onboard.web.entity.AccountEntity;
import com.onboard.web.entity.AccountPwEntity;
import com.onboard.web.model.Req.Signin;
import com.onboard.web.model.Req.Signup;
import com.onboard.web.model.Resp.AccountDto;
import com.onboard.web.repository.AccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("AuthServiceTest")
class AuthServiceTest {

    private static final String EMAIL = "test3@wanted.co.kr";
    private static final String PASSWORD = "abcd1234";

    @InjectMocks
    private AuthService authService;

    @Mock
    private AccountRepo accountRepo;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 회원가입() {
        //given
        Signup signup = createSignup();
        when(accountRepo.save(any())).thenReturn(createAccountEntity());

        //when
        AccountDto accountDto = authService.signup(signup);

        //then
        assertThat(accountDto.getEmail()).isEqualTo(signup.getEmail());
    }

    @Test
    void 로그인() {
        //given
        Signin signin = createSignin();

        //when


        //then
    }

    private Signup createSignup() {
        Signup signup = new Signup();
        signup.setEmail(EMAIL);
        signup.setPassword(PASSWORD);
        return signup;
    }

    private Signin createSignin() {
        Signin signin = new Signin();
        signin.setEmail(EMAIL);
        signin.setPassword(PASSWORD);
        return signin;
    }

    private AccountEntity createAccountEntity() {
        return AccountEntity.builder()
                .email(EMAIL)
                .build();
    }
}