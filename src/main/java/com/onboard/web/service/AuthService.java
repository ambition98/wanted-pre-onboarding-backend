package com.onboard.web.service;

import com.onboard.web.entity.AccountEntity;
import com.onboard.web.entity.AccountPwEntity;
import com.onboard.web.model.Req.SignupReq;
import com.onboard.web.repository.AccountPwRepo;
import com.onboard.web.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AccountRepo accountRepo;
    private final AccountPwRepo accountPwRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public boolean signup(SignupReq signupReq) {
        // Save new AccountPwEntity
        String digest = bCryptPasswordEncoder.encode(signupReq.getPassword());
        AccountPwEntity accountPwEntity = new AccountPwEntity(digest);
        AccountPwEntity accountPwRes = accountPwRepo.save(accountPwEntity);

        // Save new AccountEntity
        AccountEntity accountEntity = new AccountEntity(signupReq.getEmail(), accountPwRes);
//        log.info(accountEntity.getId());
//        log.info(accountEntity.getEmail());
//        log.info(accountEntity.getAccountPwEntity().getId());
//        log.info(accountEntity.getAccountPwEntity().getPassword());
        AccountEntity res =  accountRepo.save(accountEntity);

        return accountEntity.getId().equals(res.getId());
    }
}
