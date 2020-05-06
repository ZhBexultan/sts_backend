package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.ImageBlog;
import com.spring.sts.backend.repository.ImageBlogRepository;
import com.spring.sts.backend.service.ImageBlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageBlogServiceImpl implements ImageBlogService {

    @Autowired
    private ImageBlogRepository imageBlogRepository;

    @Override
    public ImageBlog getImageBlogByBlogId(Long blogId) {
        ImageBlog imageBlog = imageBlogRepository.findFirstByBlog_Id(blogId);
        if (imageBlog == null) {
            log.warn("IN ImageBlogServiceImpl getImageBlogByBlogId - no imageBlog found by id: {}", blogId);
            return null;
        }
        log.info("IN ImageBlogServiceImpl getImageBlogByBlogId - imageBlog: {} found by id: {}", imageBlog, blogId);
        return imageBlog;
    }
}
