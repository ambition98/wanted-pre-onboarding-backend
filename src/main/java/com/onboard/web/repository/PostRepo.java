package com.onboard.web.repository;

import com.onboard.web.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PostRepo extends JpaRepository<Post, Long> {}
