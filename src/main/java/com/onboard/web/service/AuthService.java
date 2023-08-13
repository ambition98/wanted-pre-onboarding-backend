package com.onboard.web.service;

import com.onboard.exception.IncorrectLoginInfo;
import com.onboard.util.JwtUtil;
import com.onboard.web.entity.AccountEntity;
import com.onboard.web.entity.AccountPwEntity;
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

    @Transactional
    public AccountDto signup(Signup signup) {
        String digest = bCryptPasswordEncoder.encode(signup.getPassword());
        AccountPwEntity accountPwEntity = new AccountPwEntity(digest);
        AccountEntity accountEntity = new AccountEntity(signup.getEmail(), accountPwEntity);
        return AccountDto.bulid(accountRepo.save(accountEntity));
    }

    public AccountDto signin(Signin signin, HttpServletResponse resp) {
        AccountEntity accountEntity
                = accountRepo.findByEmail(signin.getEmail())
                .orElseThrow(() -> new IncorrectLoginInfo("아이디 또는 패스워드가 틀립니다."));

        AccountPwEntity accountPwEntity = accountEntity.getAccountPwEntity();

        if (!bCryptPasswordEncoder.matches(signin.getPassword(), accountPwEntity.getPassword())) {
            throw new IncorrectLoginInfo("아이디 또는 패스워드가 틀립니다.");
        }

        jwtUtil.setTokenToCookie(resp, accountEntity.getId());
        return new AccountDto(accountEntity.getEmail());
    }
}
