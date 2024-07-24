package com.example.focus_api.controllers;

import com.example.focus_api.domain.feedbacks.FeedbackRepository;
import com.example.focus_api.domain.feedbacks.FeedbackStatus;
import com.example.focus_api.domain.feedbacks.Feedbacks;
import com.example.focus_api.domain.feedbacks.RequestFeedback;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(FeedbackController.class)
public class FeedbackControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackRepository feedbackRepository;

    private Feedbacks feedback;

    @BeforeEach
    void setUp() {
        feedback = new Feedbacks();
        feedback.setId("1");
        feedback.setTitle("Test Feedback");
        feedback.setAuthorName("Author");
        feedback.setContent("Content of the feedback");
        feedback.setStatus(FeedbackStatus.FEEDBACK);
    }

    @Test
    void testGetAllFeedbacks() throws Exception {
        Mockito.when(feedbackRepository.findAll()).thenReturn(Collections.singletonList(feedback));

        mockMvc.perform(get("/focus/feedback"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(feedback.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(feedback.getTitle()));
    }

    @Test
    void testGetFeedbackById() throws Exception {
        Mockito.when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.of(feedback));

        mockMvc.perform(get("/focus/feedback/{id}", feedback.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(feedback.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(feedback.getTitle()));
    }

    @Test
    void testCreateFeedback() throws Exception {
        RequestFeedback requestFeedback = new RequestFeedback("New Feedback", "New Author", "New Content");

        mockMvc.perform(post("/focus/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Feedback\", \"authorName\": \"New Author\", \"content\": \"New Content\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Feedback created"));
    }

    @Test
    void testDeleteFeedback() throws Exception {
        Mockito.when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.of(feedback));

        mockMvc.perform(delete("/focus/feedback/{id}", feedback.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Feedback deleted"));
    }
}
