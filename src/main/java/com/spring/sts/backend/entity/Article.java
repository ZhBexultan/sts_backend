package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sts_article")
public class Article extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content", length = 8000)
    private String content;

    @Column(name = "short_content", length = 512)
    private String shortContent;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "mood_id")
    private Long moodId;

    @Column(name = "problem_id")
    private Long problemId;

    @Column(name = "is_blog")
    private boolean isBlog = false;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
