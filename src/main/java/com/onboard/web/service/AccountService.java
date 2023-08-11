package com.onboard.web.service;

import com.onboard.auth.JwtState;
import com.onboard.util.JwtUtil;
import com.onboard.web.entity.AccountEntity;
import com.onboard.web.model.Resp.AccountDto;
import com.onboard.web.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
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
