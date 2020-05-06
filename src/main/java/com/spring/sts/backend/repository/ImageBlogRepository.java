package com.spring.sts.backend.repository;

import com.spring.sts.backend.entity.ImageBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public interface ImageBlogRepository extends JpaRepository<ImageBlog, Long> {

    ImageBlog findFirstByBlog_Id(Long blogId);

}
