package com.example.focus_api.domain.comments;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "comments")
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @Column(name = "author_name")
    private String authorName;

    private String content;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "post_id")
    private String postId;

    @Column(name = "post_type")
    private String postType;
}
