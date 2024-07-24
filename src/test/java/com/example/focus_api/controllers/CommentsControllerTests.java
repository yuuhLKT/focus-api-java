package com.example.focus_api.controllers;

import com.example.focus_api.domain.comments.Comments;
import com.example.focus_api.domain.comments.RequestComments;
import com.example.focus_api.service.CommentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CommentsController.class)
public class CommentsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentsService commentsService;

    private Comments comment;

    @BeforeEach
    void setUp() {
        comment = new Comments();
        comment.setId("1");
        comment.setTitle("Test Comment");
        comment.setAuthorName("Author");
        comment.setContent("Content of the comment");
        comment.setPostId("post1");
        comment.setPostType("report");
    }

    @Test
    void testCreateComment() throws Exception {
        RequestComments requestComments = new RequestComments("New Comment", "New Author", "New Content", "post1");

        Mockito.when(commentsService.createComment(Mockito.any(RequestComments.class))).thenReturn(comment);

        mockMvc.perform(post("/focus/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Comment\", \"authorName\": \"New Author\", \"content\": \"New Content\", \"postId\": \"post1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(comment.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(comment.getTitle()));
    }

    @Test
    void testGetAllComments() throws Exception {
        Mockito.when(commentsService.getAllComments()).thenReturn(Collections.singletonList(comment));

        mockMvc.perform(get("/focus/comments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(comment.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(comment.getTitle()));
    }

    @Test
    void testGetCommentById() throws Exception {
        Mockito.when(commentsService.getCommentById(comment.getId())).thenReturn(comment);

        mockMvc.perform(get("/focus/comments/{id}", comment.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(comment.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(comment.getTitle()));
    }

    @Test
    void testGetCommentsByPostId() throws Exception {
        List<Comments> commentsList = Collections.singletonList(comment);
        Mockito.when(commentsService.getCommentsByPostId(comment.getPostId())).thenReturn(commentsList);

        mockMvc.perform(get("/focus/comments/post/{postId}", comment.getPostId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(comment.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(comment.getTitle()));
    }

    @Test
    void testDeleteComment() throws Exception {
        Mockito.doNothing().when(commentsService).deleteComment(comment.getId());

        mockMvc.perform(delete("/focus/comments/{id}", comment.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
