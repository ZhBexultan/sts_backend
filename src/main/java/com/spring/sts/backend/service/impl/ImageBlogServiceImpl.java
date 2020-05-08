package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.ImageBlog;
import com.spring.sts.backend.repository.ImageBlogRepository;
import com.spring.sts.backend.service.ImageBlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<ImageBlog> getAllImageBlogs() {
        List<ImageBlog> imageBlogs = imageBlogRepository.findAll();
        log.info("IN ImageBlogServiceImpl getAllImageBlogs - {} tags found", imageBlogs.size());
        return imageBlogs;
    }

    @Override
    public ImageBlog saveImageBlog(ImageBlog imageBlog) {
        ImageBlog savedImageBlog = imageBlogRepository.save(imageBlog);
        log.info("IN ImageBlogServiceImpl saveImageBlog - imageBlog: {} successfully saved", savedImageBlog);
        return savedImageBlog;
    }

    @Override
    public void deleteImageBlog(Long id) {
        imageBlogRepository.deleteById(id);
        log.info("IN ImageBlogServiceImpl deleteImageBlog - imageBlog with id: {} successfully deleted", id);
    }

    @Override
    public ImageBlog getImageBlogById(Long id) {
        ImageBlog imageBlog = imageBlogRepository.findById(id).orElse(null);
        if (imageBlog == null) {
            log.warn("IN ImageBlogServiceImpl getImageBlogById - no imageBlog found by id: {}", id);
            return null;
        }
        log.info("IN ImageBlogServiceImpl getImageBlogById - imageBlog: {} found by id: {}", imageBlog, id);
        return imageBlog;
    }

    @Override
    public List<ImageBlog> getImageBlogsByBlogId(Long blogId) {
        List<ImageBlog> images = imageBlogRepository.findByBlog_Id(blogId);
        log.info("IN ImageBlogServiceImpl getImageBlogsByBlogId - {} images found", images.size());
        return images;
    }

}
