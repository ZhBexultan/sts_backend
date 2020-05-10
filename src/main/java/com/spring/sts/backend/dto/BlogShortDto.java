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
    private String shortContent;
    private LocalDateTime createdDate;
    private ImageBlogDto image;

    public Blog toBlog() {
        return new Blog();
    }

    public static BlogShortDto fromBlog(Blog blog, ImageBlogDto image) {
        BlogShortDto blogShortDto = new BlogShortDto();
        blogShortDto.setId(blog.getId());
        blogShortDto.setTitle(blog.getTitle());
        blogShortDto.setShortContent(blog.getShortContent());
        blogShortDto.setCreatedDate(blog.getCreatedDate());
        blogShortDto.setImage(image);
        return blogShortDto;
    }

}
