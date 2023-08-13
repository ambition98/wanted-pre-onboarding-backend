package com.onboard.web.repository;

import com.onboard.web.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PostRepo extends JpaRepository<PostEntity, Long> {
//    @Override
//    List<PostEntity> findAll(Pageable pageable);
}
