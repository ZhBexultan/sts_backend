package com.spring.sts.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.sts.backend.entity.Blog;
import com.spring.sts.backend.entity.ImageBlog;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogDto {

    private Long id;
    private String title;
    private String content;
    private String shortContent;
    private LocalDateTime createdDate;
    private UserShortDto userShortDto;
    private List<ImageBlogDto> images;

    public Blog toBlog() {
        return new Blog();
    }

    public static BlogDto fromBlog(Blog blog, List<ImageBlog> images) {
        List<ImageBlogDto> imageBlogDtos = new ArrayList<>();
        for (ImageBlog imageArticle: images) {
            imageBlogDtos.add(ImageBlogDto.fromImageBlog(imageArticle));
        }
        BlogDto blogDto = new BlogDto();
        blogDto.setId(blog.getId());
        blogDto.setTitle(blog.getTitle());
        blogDto.setContent(blog.getContent());
        blogDto.setShortContent(blog.getShortContent());
        blogDto.setCreatedDate(blog.getCreatedDate());
        blogDto.setUserShortDto(UserShortDto.fromUser(blog.getUser()));
        blogDto.setImages(imageBlogDtos);
        return  blogDto;
    }

}
