package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.Blog;
import com.spring.sts.backend.entity.Status;
import com.spring.sts.backend.repository.BlogRepository;
import com.spring.sts.backend.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog saveBlog(Blog blog) {
        Blog savedBlog = blogRepository.save(blog);
        log.info("IN BlogServiceImpl saveBlog - blog: {} successfully saved", savedBlog);
        return savedBlog;
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
        log.info("IN BlogServiceImpl deleteBlog - blog with id: {} successfully deleted", id);
    }

    @Override
    public Blog getBlogById(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            log.warn("IN BlogServiceImpl getBlogById - no blog found by id: {}", id);
            return null;
        }
        log.info("IN BlogServiceImpl getBlogById - blog: {} found by id: {}", blog, id);
        return blog;
    }

    @Override
    public List<Blog> getBlogsByUserId(Long userId) {
        List<Blog> blogs = blogRepository.findByUserId(userId);
        log.info("IN BlogServiceImpl getBlogsByUserId - {} blogs found", blogs.size());
        return blogs;
    }

    @Override
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        log.info("IN BlogServiceImpl getAllBlogs - {} blogs found", blogs.size());
        return blogs;
    }

    @Override
    public List<Blog> getBlogsStatusIsAccepted() {
        List<Blog> blogs = blogRepository.findBlogsByStatus(Status.ACCEPTED);
        log.info("IN BlogServiceImpl getBlogsStatusIsAccepted - {} blogs found", blogs.size());
        return blogs;
    }

    @Override
    public List<Blog> getBlogsStatusIsCreated() {
        List<Blog> blogs = blogRepository.findBlogsByStatus(Status.CREATED);
        log.info("IN BlogServiceImpl getBlogsStatusIsCreated - {} blogs found", blogs.size());
        return blogs;
    }

    @Override
    public List<Blog> getBlogsStatusIsDenied() {
        List<Blog> blogs = blogRepository.findBlogsByStatus(Status.DENIED);
        log.info("IN BlogServiceImpl getBlogsStatusIsDenied - {} blogs found", blogs.size());
        return blogs;
    }
}
