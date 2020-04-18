package com.spring.sts.backend.repository;

import com.spring.sts.backend.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByCategoryId(Long categoryId);
    /*List<Article> findByMoodId(Long moodId);
    List<Article> findByProblemId(Long problemId);*/
    List<Article> findByCategoryIdAndAndMoodIdAndProblemId(Long categoryId, Long moodId, Long problemId);
}
