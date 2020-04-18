package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.Blog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {

    Blog saveBlog(Blog blog);

    void deleteBlog(Long id);

    Blog getBlogById(Long id);

    List<Blog> getBlogsByUserId(Long userId);

    List<Blog> getAllBlogs();

}
