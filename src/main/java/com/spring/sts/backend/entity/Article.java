package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sts_article")
public class Article extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "short_content", nullable = false)
    private String shortContent;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "mood_id")
    private Long moodId;

    @Column(name = "problem_id", nullable = false)
    private Long problemId;

    @Column(name = "is_blog", nullable = false)
    private boolean isBlog = false;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
