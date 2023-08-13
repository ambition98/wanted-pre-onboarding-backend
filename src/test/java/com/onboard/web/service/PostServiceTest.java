package com.onboard.web.service;

import com.onboard.exception.UnauthorizedException;
import com.onboard.web.entity.AccountEntity;
import com.onboard.web.entity.PostEntity;
import com.onboard.web.model.Req.SaveNewPost;
import com.onboard.web.model.Req.UpdatePost;
import com.onboard.web.model.Resp.PostDto;
import com.onboard.web.repository.AccountRepo;
import com.onboard.web.repository.PostRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("PostServiceTest")
class PostServiceTest {

    private static final String ACCOUNT_ID = "id";
    private static final int POST_SIZE = 10;
    private static final Long POST_ID = 1L;
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String EMAIL = "test@wanted.co.kr";

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepo postRepo;

    @Mock
    private AccountRepo accountRepo;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void POST_목록_가져오기() {
//        //given
//        List<PostEntity> postEntities = createPosts();
//        when(postRepo.findAll((PageRequest) any()))
//                .thenReturn(postEntities);
//
//        //when
//        List<PostDto> posts = postService.getPosts(1);
//
//        //then
//        assertThat(posts.size()).isEqualTo(POST_SIZE);
//        for (int i=0; i<POST_SIZE; i++) {
//            PostEntity entity = postEntities.get(i);
//            PostDto dto = posts.get(i);
//            assertThat(dto.getId()).isEqualTo(entity.getId());
//            assertThat(dto.getTitle()).isEqualTo(entity.getTitle());
//            assertThat(dto.getContent()).isEqualTo(entity.getContent());
//        }
//    }

    @Test
    void POST_한_개_가져오기() {
        //given
        PostEntity postEntity = createPostEntity();
        when(postRepo.findById(any())).thenReturn(Optional.of(postEntity));

        //when
        PostDto postDto = postService.getById(postEntity.getId());

        //then
        assertThat(postDto.getId()).isEqualTo(postEntity.getId());
    }

    @Test
    void 새로운_POST_저장() {
        //given
        SaveNewPost saveNewPost = new SaveNewPost();
        saveNewPost.setTitle(TITLE);
        saveNewPost.setContent(CONTENT);
        PostEntity postEntity = createPostEntity();
        AccountEntity accountEntity = createAccountEntity();

        when(accountRepo.findById(any())).thenReturn(Optional.of(accountEntity));
        when(postRepo.save(any())).thenReturn(postEntity);

        //when
        PostDto postDto = postService.saveNewPost(saveNewPost, ACCOUNT_ID);

        //then
        assertThat(postDto.getTitle()).isEqualTo(saveNewPost.getTitle());
        assertThat(postDto.getContent()).isEqualTo(saveNewPost.getContent());
        assertThat(postDto.getWriterEmail()).isEqualTo(accountEntity.getEmail());
    }

    @Test
    void Post_수정() {
        //given
        UpdatePost updatePost = createUpdatePost();
        PostEntity postEntity = createPostEntity();

        when(postRepo.findById(POST_ID)).thenReturn(Optional.of(postEntity));
        when(postRepo.save(any())).thenReturn(postEntity);

        //when
        PostDto postDto = postService.updateById(POST_ID, updatePost, ACCOUNT_ID);

        //then
        assertThat(postDto.getId()).isEqualTo(POST_ID);
        assertThat(postDto.getId()).isEqualTo(postEntity.getId());
        assertThat(postDto.getTitle()).isEqualTo(updatePost.getTitle());
        assertThat(postDto.getContent()).isEqualTo(updatePost.getContent());
    }

    @Test
    void Post_삭제() {
        //given
        PostEntity postEntity = createPostEntity();
        when(postRepo.findById(postEntity.getId())).thenReturn(Optional.of(postEntity));

        //when
        postService.deleteById(postEntity.getId(), ACCOUNT_ID);
        when(postRepo.findById(any())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> postService.deleteById(postEntity.getId(), ACCOUNT_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 존재하지_않는_postId() {
        //given
        Long wrongId = -1L;
        when(postRepo.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> postService.getById(wrongId))
                .isInstanceOf(EntityNotFoundException.class);
        assertThatThrownBy(() -> postService.updateById(wrongId, new UpdatePost(), ACCOUNT_ID))
                .isInstanceOf(EntityNotFoundException.class);
        assertThatThrownBy(() -> postService.deleteById(wrongId, ACCOUNT_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 존재하지_않는_accountID() {
        //given
        String wrongId = "wrong";
        when(accountRepo.findById(wrongId)).thenThrow(new EntityNotFoundException());

        //when
        //then
        assertThatThrownBy(() -> postService.saveNewPost(new SaveNewPost(), wrongId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 권한이_없는_accountId() {
        //given
        String wrongAccountId = "wrongId";
        UpdatePost updatePost = createUpdatePost();
        when(postRepo.findById(any())).thenReturn(Optional.of(createPostEntity()));

        //when
        //then
        assertThatThrownBy(() -> postService.updateById(POST_ID, updatePost, wrongAccountId))
                .isInstanceOf(UnauthorizedException.class);
        assertThatThrownBy(() -> postService.deleteById(POST_ID, wrongAccountId))
                .isInstanceOf(UnauthorizedException.class);
    }

    private List<PostEntity> createPosts() {
        List<PostEntity> posts = new ArrayList<>(POST_SIZE);
        for (int i = 0; i< POST_SIZE; i++) {
            posts.add(createPostEntity());
        }

        return posts;
    }

    private PostEntity createPostEntity() {
        return PostEntity.builder()
                .id(POST_ID)
                .title(TITLE)
                .content(CONTENT)
                .writer(createAccountEntity())
                .build();
    }

    private AccountEntity createAccountEntity() {
        return AccountEntity.builder()
                .id(ACCOUNT_ID)
                .email(EMAIL)
                .build();
    }

    private UpdatePost createUpdatePost() {
        String updatedTitle = "uTitle";
        String updatedContent = "uContent";
        UpdatePost updatePost = new UpdatePost();
        updatePost.setTitle(updatedTitle);
        updatePost.setContent(updatedContent);
        return updatePost;
    }
}