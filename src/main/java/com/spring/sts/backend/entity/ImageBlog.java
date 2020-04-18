package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sts_image_blog")
public class ImageBlog extends ImageBase {

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

}
