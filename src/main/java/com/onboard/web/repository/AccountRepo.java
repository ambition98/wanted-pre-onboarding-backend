package com.onboard.web.repository;

import com.onboard.web.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepo extends JpaRepository<AccountEntity, String> {
    @Transactional(readOnly = true)
    public Optional<AccountEntity> findByEmail(String email);
}
