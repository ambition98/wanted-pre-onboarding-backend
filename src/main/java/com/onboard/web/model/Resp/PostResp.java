package com.onboard.web.model.Resp;

import com.onboard.web.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostResp {
    private Long id;
    private String title;
    private String content;
    private String writerEmail;

    public static PostResp bulid(Post post) {
        PostResp postResp = new PostResp();
        postResp.setId(post.getId());
        postResp.setTitle(post.getTitle());
        postResp.setContent(post.getContent());
        postResp.setWriterEmail(post.getWriter().getEmail());
        return postResp;
    }
}
