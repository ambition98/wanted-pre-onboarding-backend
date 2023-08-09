package com.onboard.web.service;

import com.onboard.web.entity.AccountEntity;
import com.onboard.web.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepo accountRepo;

    AccountEntity getById(String id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

    AccountEntity getByEmail(String email) {
        return accountRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

//    AccountEntity saveNewAccount(String email, String pw) {
//        getByLogin(login)
//    }
}
