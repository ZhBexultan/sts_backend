package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.ImageBlog;
import org.springframework.stereotype.Service;

@Service
public interface ImageBlogService {
    ImageBlog getImageBlogByBlogId(Long blogId);
}
