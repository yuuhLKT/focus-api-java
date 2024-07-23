package com.example.focus_api.domain.feedbacks;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "feedbacks")
@Table(name = "feedbacks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Feedbacks {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @Column(name = "author_name")
    private String authorName;

    private String content;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedbackStatus status;

    public Feedbacks(RequestFeedback requestFeedback) {
        this.title = requestFeedback.title();
        this.authorName = requestFeedback.authorName();
        this.content = requestFeedback.content();
        this.createdAt = new Date();
        this.status = FeedbackStatus.FEEDBACK;
    }
}
