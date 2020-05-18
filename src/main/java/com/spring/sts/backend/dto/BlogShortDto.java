package com.spring.sts.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.sts.backend.entity.Blog;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogShortDto {

    private Long id;
    private String title;
    private String content;
    private String shortContent;
    private String status;
    private String comment;
    private boolean isBlog;
    private LocalDateTime createdDate;
    private ImageBlogDto image;

    public Blog toBlog() {
        return new Blog();
    }

    public static BlogShortDto fromBlog(Blog blog, ImageBlogDto image) {
        BlogShortDto blogShortDto = new BlogShortDto();
        blogShortDto.setId(blog.getId());
        blogShortDto.setTitle(blog.getTitle());
        blogShortDto.setContent(blog.getContent());
        blogShortDto.setShortContent(blog.getShortContent());
        blogShortDto.setStatus(blog.getStatus().name());
        blogShortDto.setComment(blog.getComment());
        blogShortDto.setBlog(blog.isBlog());
        blogShortDto.setCreatedDate(blog.getCreatedDate());
        blogShortDto.setImage(image);
        return blogShortDto;
    }

}
