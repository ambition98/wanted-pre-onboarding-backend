package com.onboard.web.repository;

import com.onboard.web.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepo extends JpaRepository<Account, String> {
    @Transactional(readOnly = true)
    Optional<Account> findByEmail(String email);
}
