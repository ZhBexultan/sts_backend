package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.Blog;
import com.spring.sts.backend.entity.Status;
import com.spring.sts.backend.exception.BodyIsNullException;
import com.spring.sts.backend.exception.NoReturnDataException;
import com.spring.sts.backend.exception.ObjectNotFoundException;
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
        if (blog.getTitle() == null || blog.getContent() == null || blog.getShortContent() == null) {
            throw new BodyIsNullException("Blog", "title, content, shortContent");
        }
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
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Blog", id));
        log.info("IN BlogServiceImpl getBlogById - blog: {} found by id: {}", blog, id);
        return blog;
    }

    @Override
    public List<Blog> getBlogsByUserId(Long userId) {
        List<Blog> blogs = blogRepository.findByUserId(userId);
        if (blogs.isEmpty()) {
            throw new NoReturnDataException("Blogs of User ID: " + userId);
        }
        log.info("IN BlogServiceImpl getBlogsByUserId - {} blogs found", blogs.size());
        return blogs;
    }

    @Override
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        if (blogs.isEmpty()) {
            throw new NoReturnDataException("Blogs");
        }
        log.info("IN BlogServiceImpl getAllBlogs - {} blogs found", blogs.size());
        return blogs;
    }

    @Override
    public List<Blog> getBlogsStatusIsAccepted() {
        List<Blog> blogs = blogRepository.findBlogsByStatus(Status.ACCEPTED);
        if (blogs.isEmpty()) {
            throw new NoReturnDataException("Accepted Blogs");
        }
        log.info("IN BlogServiceImpl getBlogsStatusIsAccepted - {} blogs found", blogs.size());
        return blogs;
    }

    @Override
    public List<Blog> getBlogsStatusIsCreated() {
        List<Blog> blogs = blogRepository.findBlogsByStatus(Status.CREATED);
        if (blogs.isEmpty()) {
            throw new NoReturnDataException("Created Blogs");
        }
        log.info("IN BlogServiceImpl getBlogsStatusIsCreated - {} blogs found", blogs.size());
        return blogs;
    }

    @Override
    public List<Blog> getBlogsStatusIsDenied() {
        List<Blog> blogs = blogRepository.findBlogsByStatus(Status.DENIED);
        if (blogs.isEmpty()) {
            throw new NoReturnDataException("Denied Blogs");
        }
        log.info("IN BlogServiceImpl getBlogsStatusIsDenied - {} blogs found", blogs.size());
        return blogs;
    }

    @Override
    public List<Blog> getBlogsStatusIsDraft() {
        List<Blog> blogs = blogRepository.findBlogsByStatus(Status.DRAFT);
        if (blogs.isEmpty()) {
            throw new NoReturnDataException("Draft Blogs");
        }
        log.info("IN BlogServiceImpl getBlogsStatusIsDraft - {} blogs found", blogs.size());
        return blogs;
    }
}
