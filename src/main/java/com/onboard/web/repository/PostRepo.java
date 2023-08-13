package com.onboard.web.repository;

import com.onboard.web.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostRepo extends JpaRepository<PostEntity, Long> {
//    Page<PostEntity> findAll(PageRequest pageRequest);
}
