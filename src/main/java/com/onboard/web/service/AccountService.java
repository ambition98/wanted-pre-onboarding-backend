package com.onboard.web.service;

import com.onboard.util.JwtUtil;
import com.onboard.web.entity.AccountEntity;
import com.onboard.web.model.Resp.AccountDto;
import com.onboard.web.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepo accountRepo;
    private final JwtUtil jwtUtil;

    public AccountDto getById(String id) {
        AccountEntity accountEntity = accountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("계정을 찾을 수 없습니다."));

        return AccountDto.bulid(accountEntity);
    }

    public AccountDto getByEmail(String email) {
        AccountEntity accountEntity = accountRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("계정을 찾을 수 없습니다."));

        return AccountDto.bulid(accountEntity);
    }
}
