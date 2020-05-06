package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sts_blog")
public class Blog extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "short_content", nullable = false)
    private String shortContent;

    @Column(name = "is_blog", nullable = false)
    private boolean isBlog;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(mappedBy = "blogs")
    private Set<Tag> employees = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}

