package com.example.focus_api.service;

import com.example.focus_api.domain.comments.Comments;
import com.example.focus_api.domain.comments.CommentsRepository;
import com.example.focus_api.domain.comments.RequestComments;
import com.example.focus_api.domain.feedbacks.FeedbackRepository;
import com.example.focus_api.domain.reports.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final ReportRepository reportRepository;
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository, ReportRepository reportRepository, FeedbackRepository feedbackRepository) {
        this.commentsRepository = commentsRepository;
        this.reportRepository = reportRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public Comments createComment(RequestComments request) {
        String postType = "";
        if (reportRepository.existsById(request.postId())) {
            postType = "report";
        } else if (feedbackRepository.existsById(request.postId())) {
            postType = "feedback";
        } else {
            throw new IllegalArgumentException("Invalid post_id");
        }

        Comments comment = new Comments();
        comment.setTitle(request.title());
        comment.setAuthorName(request.authorName());
        comment.setContent(request.content());
        comment.setPostId(request.postId());
        comment.setPostType(postType);
        comment.setCreatedAt(new Date());

        return commentsRepository.save(comment);
    }

    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    public Comments getCommentById(String id) {
        return commentsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comment_id"));
    }

    public List<Comments> getCommentsByPostId(String postId) {
        return commentsRepository.findByPostId(postId);
    }

    public void deleteComment(String id) {
        commentsRepository.deleteById(id);
    }
}
