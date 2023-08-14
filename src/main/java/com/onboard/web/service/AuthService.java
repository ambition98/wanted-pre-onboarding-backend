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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AccountRepo accountRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @Transactional
    public AccountDto signup(Signup signup) {
        String digest = bCryptPasswordEncoder.encode(signup.getPassword());
        AccountPw accountPw = new AccountPw(digest);
        Account account = new Account(signup.getEmail(), accountPw);
        return AccountDto.bulid(accountRepo.save(account));
    }

    public AccountDto signin(Signin signin, HttpServletResponse resp) {
        Account account = accountRepo.findByEmail(signin.getEmail())
                .orElseThrow(() -> new IncorrectLoginInfo("아이디 또는 패스워드가 틀립니다."));

        AccountPw accountPw = account.getAccountPw();

        if (!bCryptPasswordEncoder.matches(signin.getPassword(), accountPw.getPassword())) {
            throw new IncorrectLoginInfo("아이디 또는 패스워드가 틀립니다.");
        }

        String tk = jwtUtil.makeUserToken(account.getId());
        cookieUtil.setTokenToCookie(resp, tk);
        return new AccountDto(account.getEmail());
    }
}
