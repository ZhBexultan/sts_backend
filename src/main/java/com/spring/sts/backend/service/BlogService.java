package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.Blog;
import com.spring.sts.backend.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {

    Blog saveBlog(Blog blog);

    void deleteBlog(Long id);

    Blog getBlogById(Long id);

    Blog getBlogById(Long id, User user);

    List<Blog> getBlogsByUserId(Long userId);

    List<Blog> getAllBlogs();

    List<Blog> getBlogsStatusIsAccepted();

    List<Blog> getBlogsStatusIsCreated();

    List<Blog> getBlogsStatusIsDenied();

    List<Blog> getBlogsStatusIsDraft();
}
