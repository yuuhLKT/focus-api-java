package com.example.focus_api.controllers;

import com.example.focus_api.domain.feedbacks.FeedbackRepository;
import com.example.focus_api.domain.feedbacks.Feedbacks;
import com.example.focus_api.domain.feedbacks.RequestFeedback;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/focus/feedback")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }


    @GetMapping
    public ResponseEntity getAllFeedbacks() {
        var allFeedbacks = feedbackRepository.findAll();
        return ResponseEntity.ok(allFeedbacks);
    }

    @GetMapping("/{id}")
    public ResponseEntity getFeedbackById(@PathVariable("id") String id) {
        return feedbackRepository.findById(id)
                .map(feedback -> ResponseEntity.ok(feedback))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity createFeedback(@RequestBody @Valid RequestFeedback data) {
        Feedbacks feedbacks = new Feedbacks(data);
        feedbackRepository.save(feedbacks);
        return ResponseEntity.ok("Feedback created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFeedback(@PathVariable("id") String id) {
        return feedbackRepository.findById(id)
                .map(feedback -> {
                    feedbackRepository.delete(feedback);
                    return ResponseEntity.ok("Feedback deleted");
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
