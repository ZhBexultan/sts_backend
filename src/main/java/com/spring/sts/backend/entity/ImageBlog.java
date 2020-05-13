package com.spring.sts.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sts_image_blog")
public class ImageBlog extends ImageBase {

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

}

