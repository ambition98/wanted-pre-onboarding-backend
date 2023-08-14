package com.onboard.web.respository;

import com.onboard.web.entity.Account;
import com.onboard.web.entity.AccountPw;
import com.onboard.web.repository.AccountRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepoTest {

    @Autowired
    private AccountRepo accountRepo;

    @Test
    void 신규_유저_저장() {
        //given
        Account account = createAccountEntity();

        //when
        Account resultEntity = accountRepo.save(account);

        //then
        assertThat(account.getId()).isEqualTo(resultEntity.getId());
        assertThat(account.getEmail()).isEqualTo(resultEntity.getEmail());
        assertThat(account.getAccountPw().getId()).isEqualTo(resultEntity.getAccountPw().getId());
        assertThat(account.getAccountPw().getPassword()).isEqualTo(resultEntity.getAccountPw().getPassword());
    }

    @Test
    void 유저_조회() {
        //given
        Account account = accountRepo.save(createAccountEntity());

        //when
        Account resultEntity = accountRepo.findById(account.getId())
                .orElseThrow(() -> new EntityNotFoundException(""));

        //then
        assertThat(account.getId()).isEqualTo(resultEntity.getId());
        assertThat(account.getEmail()).isEqualTo(resultEntity.getEmail());
        assertThat(account.getAccountPw().getId()).isEqualTo(resultEntity.getAccountPw().getId());
        assertThat(account.getAccountPw().getPassword()).isEqualTo(resultEntity.getAccountPw().getPassword());
    }

    private Account createAccountEntity() {
        return Account.builder()
                .id("id")
                .email("test@wanted.co.kr")
                .accountPw(createAccountPwEntity())
                .build();
    }

    private AccountPw createAccountPwEntity() {
        return new AccountPw("pw");
    }
}
