package com.spring.sts.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.sts.backend.entity.ImageBlog;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageBlogDto {

    private Long id;
    private String url;

    public ImageBlog toImageBlog() {
        return new ImageBlog();
    }

    public static ImageBlogDto fromImageBlog(ImageBlog imageBlog) {
        ImageBlogDto imageBlogDto = new ImageBlogDto();
        imageBlogDto.setId(imageBlog.getId());
        imageBlogDto.setUrl(imageBlog.getUrl());
        return  imageBlogDto;
    }

}
