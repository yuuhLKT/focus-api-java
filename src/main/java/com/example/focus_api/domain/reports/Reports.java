package com.example.focus_api.domain.reports;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "reports")
@Table(name = "reports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reports {

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
    private ReportStatus status;

    public Reports(RequestReport requestReport) {
        this.title = requestReport.title();
        this.authorName = requestReport.authorName();
        this.content = requestReport.content();
        this.createdAt = new Date();
        this.status = ReportStatus.OPEN;
    }
}
