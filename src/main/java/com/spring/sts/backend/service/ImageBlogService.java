package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.ImageBlog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageBlogService {

    ImageBlog getImageBlogByBlogId(Long blogId);

    ImageBlog saveImageBlog(ImageBlog imageBlog);

    void deleteImageBlog(Long id);

    ImageBlog getImageBlogById(Long id);

    List<ImageBlog> getAllImageBlogs();

}
