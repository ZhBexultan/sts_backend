package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "mood_id")
    private Long moodId;

    @Column(name = "problem_id", nullable = false)
    private Long problemId;

}
