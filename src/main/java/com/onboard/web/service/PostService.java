package com.onboard.web.service;

import com.onboard.exception.UnauthorizedException;
import com.onboard.web.entity.AccountEntity;
import com.onboard.web.entity.PostEntity;
import com.onboard.web.model.Req.SaveNewPost;
import com.onboard.web.model.Req.UpdatePost;
import com.onboard.web.model.Resp.PostDto;
import com.onboard.web.repository.AccountRepo;
import com.onboard.web.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepo postRepo;
    private final AccountRepo accountRepo;

    public List<PostDto> getPosts(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        List<PostEntity> postEntities = postRepo.findAllWithPage(pageRequest);
        List<PostDto> posts = new ArrayList<>();
        for (PostEntity postEntity : postEntities) {
            posts.add(PostDto.bulid(postEntity));
        }

        return posts;
    }

    public PostDto saveNewPost(SaveNewPost saveNewPost, String accountId) {
        AccountEntity accountEntity = accountRepo.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("계정을 찾을 수 없습니다."));

        PostEntity postEntity = PostEntity.builder()
                .title(saveNewPost.getTitle())
                .content(saveNewPost.getContent())
                .writer(accountEntity)
                .build();

        return PostDto.bulid(postRepo.save(postEntity));
    }

    public PostDto getById(Long postId) {
        return PostDto.bulid(getExistsEntity(postId));
    }

    public void deleteById(Long postId, String accountId) {
        PostEntity postEntity = getExistsEntity(postId);

        if (postEntity.getWriter().getId().equals(accountId)) {
            postRepo.deleteById(postEntity.getId());
        } else {
            throw new UnauthorizedException("삭제 권한이 없습니다.");
        }
    }

    public PostDto updateById(Long postId, UpdatePost updatePost, String accountId) {
       PostEntity postEntity = getExistsEntity(postId);

        if (postEntity.getWriter().getId().equals(accountId)) {
            postEntity.setTitle(updatePost.getTitle());
            postEntity.setContent(updatePost.getContent());
            return PostDto.bulid(postRepo.save(postEntity));

        } else {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }
    }

    private PostEntity getExistsEntity(Long id) {
        return postRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
    }

}
