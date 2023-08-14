package com.onboard.web.service;

import com.onboard.exception.IncorrectLoginInfo;
import com.onboard.util.CookieUtil;
import com.onboard.util.JwtUtil;
import com.onboard.web.entity.Account;
import com.onboard.web.entity.AccountPw;
import com.onboard.web.model.Req.Signin;
import com.onboard.web.model.Req.Signup;
import com.onboard.web.model.Resp.AccountDto;
import com.onboard.web.repository.AccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    private static final String EMAIL = "test3@wanted.co.kr";
    private static final String PASSWORD = "abcd1234";
    private static final String TOKEN_NAME = "tk";
    private static final String TOKEN_VALUE = "Token value";

    @InjectMocks
    private AuthService authService;
    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Spy
    private CookieUtil cookieUtil; //signin 종속, response 쿠키 테스트용
    @Mock
    private JwtUtil jwtUtil;
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
        Account account = createAccountEntity();
        when(accountRepo.save(any())).thenReturn(account);

        //when
        AccountDto accountDto = authService.signup(signup);

        //then
        assertThat(accountDto.getEmail()).isEqualTo(signup.getEmail());
    }

    @Test
    void 로그인() {
        //given
        Signin signin = createSignin();
        Account account = createAccountEntity();
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(accountRepo.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
        when(jwtUtil.makeUserToken(any())).thenReturn(TOKEN_VALUE);

        //when
        AccountDto accountDto = authService.signin(signin, response);

        //then
        assertThat(accountDto.getEmail()).isEqualTo(signin.getEmail());
        assertThat(Objects.requireNonNull(response.getCookie(TOKEN_NAME))
                .getValue()).isEqualTo(TOKEN_VALUE);
    }

    @Test
    void 로그인_실패() {
        //given
        Signin signin = createSignin();
        Account account = Account.builder()
                .email(EMAIL)
                .accountPw(new AccountPw("ActualPw"))
                .build();
        HttpServletResponse response = new MockHttpServletResponse();
        when(accountRepo.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
        when(accountRepo.findByEmail(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> authService.signin(signin, response))
                .isInstanceOf(IncorrectLoginInfo.class);
        signin.setEmail("wrongEmail");
        assertThatThrownBy(() -> authService.signin(signin, response))
                .isInstanceOf(IncorrectLoginInfo.class);
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

    private Account createAccountEntity() {
        return Account.builder()
                .email(EMAIL)
                .accountPw(createAccountPwEntity())
                .build();
    }

    private AccountPw createAccountPwEntity() {
        return new AccountPw(bCryptPasswordEncoder.encode(PASSWORD));
    }
}