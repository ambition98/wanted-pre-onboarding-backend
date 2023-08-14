package com.onboard.web.service;

import com.onboard.exception.UnauthorizedException;
import com.onboard.web.entity.Account;
import com.onboard.web.entity.Post;
import com.onboard.web.model.Req.SaveNewPost;
import com.onboard.web.model.Req.UpdatePost;
import com.onboard.web.model.Resp.PostResp;
import com.onboard.web.repository.AccountRepo;
import com.onboard.web.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

    public List<PostResp> getPosts(Pageable pageable) {
        List<Post> posts = postRepo.findAll(pageable).getContent();
        List<PostResp> postRespList = new ArrayList<>(posts.size());
        for (Post post : posts) {
            postRespList.add(PostResp.bulid(post));
        }

        return postRespList;
    }

    public PostResp saveNewPost(SaveNewPost saveNewPost, String accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("계정을 찾을 수 없습니다."));

        Post post = Post.builder()
                .title(saveNewPost.getTitle())
                .content(saveNewPost.getContent())
                .writer(account)
                .build();

        return PostResp.bulid(postRepo.save(post));
    }

    public PostResp getById(Long postId) {
        return PostResp.bulid(getExistsEntity(postId));
    }

    public void deleteById(Long postId, String accountId) {
        Post post = getExistsEntity(postId);

        if (post.getWriter().getId().equals(accountId)) {
            postRepo.deleteById(post.getId());
        } else {
            throw new UnauthorizedException("삭제 권한이 없습니다.");
        }
    }

    public PostResp updateById(UpdatePost updatePost, String accountId) {
       Post post = getExistsEntity(updatePost.getId());

        if (post.getWriter().getId().equals(accountId)) {
            post.setTitle(updatePost.getTitle());
            post.setContent(updatePost.getContent());
            return PostResp.bulid(postRepo.save(post));

        } else {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }
    }

    private Post getExistsEntity(Long id) {
        return postRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
    }

}
