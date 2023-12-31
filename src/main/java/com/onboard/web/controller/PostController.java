package com.onboard.web.controller;

import com.onboard.exception.UnauthorizedException;
import com.onboard.web.model.Req.SaveNewPost;
import com.onboard.web.model.Req.UpdatePost;
import com.onboard.web.model.Resp.CommonResp;
import com.onboard.web.model.Resp.RespBuilder;
import com.onboard.web.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<CommonResp> getPosts(Pageable pageable) {
        return RespBuilder.make(HttpStatus.OK, "Succeed", postService.getPosts(pageable));
    }

    @GetMapping("{postId}")
    public ResponseEntity<CommonResp> getPost(@PathVariable Long postId) {
        return RespBuilder.make(HttpStatus.OK, "Succeed", postService.getById(postId));
    }

    @PostMapping("")
    public ResponseEntity<CommonResp> saveNewPost(@RequestBody SaveNewPost saveNewPost, Principal principal) {
        String accountId = getExistsAccountId(principal);
        return RespBuilder.make(HttpStatus.OK, "Succeed", postService.saveNewPost(saveNewPost, accountId));
    }

    @PatchMapping("")
    public ResponseEntity<CommonResp> updatePost(@RequestBody UpdatePost updatePost,
                                                 Principal principal) {

        String accountId = getExistsAccountId(principal);
        return RespBuilder.make(HttpStatus.OK, "Succeed", postService.updateById(updatePost, accountId));
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<CommonResp> deletePost(@PathVariable Long postId, Principal principal) {
        String accountId = getExistsAccountId(principal);
        postService.deleteById(postId, accountId);
        return RespBuilder.make(HttpStatus.OK, "Succeed");
    }

    private String getExistsAccountId(Principal principal) {
        if (principal == null)
            throw new UnauthorizedException("로그인이 필요합니다.");

        return principal.getName();
    }
}
