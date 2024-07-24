package com.example.focus_api.controllers;

import com.example.focus_api.domain.comments.Comments;
import com.example.focus_api.domain.comments.RequestComments;
import com.example.focus_api.service.CommentsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/focus/comments")
public class CommentsController {

    private final CommentsService commentsService;


    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping
    public ResponseEntity<Comments> createComment(@RequestBody @Valid RequestComments request) {
        Comments createdComment = commentsService.createComment(request);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping
    public ResponseEntity<List<Comments>> getAllComments() {
        List<Comments> comments = commentsService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comments> getCommentById(@PathVariable String id) {
        Comments comment = commentsService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comments>> getCommentsByPostId(@PathVariable String postId) {
        List<Comments> comments = commentsService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        commentsService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
