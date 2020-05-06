package com.spring.sts.backend.repository;

import com.spring.sts.backend.entity.Blog;
import com.spring.sts.backend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByUserId(Long userId);
    List<Blog> findBlogsByStatus(Status status);
}
