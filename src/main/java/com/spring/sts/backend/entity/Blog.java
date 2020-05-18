package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sts_blog")
public class Blog extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content", length = 8000)
    private String content;

    @Column(name = "short_content", length = 512)
    private String shortContent;

    @Column(name = "is_blog")
    private boolean isBlog;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "comment", length = 1024)
    private String comment;

    @ManyToMany(mappedBy = "blogs")
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

