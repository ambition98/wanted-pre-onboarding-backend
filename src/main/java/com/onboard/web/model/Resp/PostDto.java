package com.onboard.web.model.Resp;

import com.onboard.web.entity.PostEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String writerEmail;

    public static PostDto bulid(PostEntity postEntity) {
        PostDto postDto = new PostDto();
        postDto.setId(postEntity.getId());
        postDto.setTitle(postEntity.getTitle());
        postDto.setContent(postEntity.getContent());
        postDto.setWriterEmail(postEntity.getWriter().getEmail());
        return postDto;
    }
}
