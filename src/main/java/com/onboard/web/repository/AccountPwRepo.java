package com.onboard.web.repository;

import com.onboard.web.entity.AccountPwEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccountPwRepo extends JpaRepository<AccountPwEntity, String> {}
