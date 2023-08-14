package com.onboard.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onboard.util.CookieUtil;
import com.onboard.util.JwtUtil;
import com.onboard.web.model.Req.SaveNewPost;
import com.onboard.web.model.Req.UpdatePost;
import com.onboard.web.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private CookieUtil cookieUtil;

    private final Gson gson = new GsonBuilder().create();

    @Test
    void 유효하지_않은_AccountId() throws Exception {
        //given
        SaveNewPost saveNewPost = new SaveNewPost();
        saveNewPost.setTitle("title");
        saveNewPost.setContent("content");
        UpdatePost updatePost = new UpdatePost();
        updatePost.setId(1L);
        updatePost.setTitle("title");
        updatePost.setContent("content");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "3");
        params.add("size", "10");

        //when
        //then
        mockMvc.perform(post("/post")
                        .params(params)
                        .content(gson.toJson(saveNewPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        mockMvc.perform(patch("/post")
                        .content(gson.toJson(updatePost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/post/1")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}