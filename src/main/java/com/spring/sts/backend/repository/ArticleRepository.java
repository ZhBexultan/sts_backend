package com.spring.sts.backend.repository;

import com.spring.sts.backend.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByCategoryId(Long categoryId);
    List<Article> findByCategoryIdAndAndMoodIdAndProblemId(Long categoryId, Long moodId, Long problemId);
    List<Article> findByCategoryIdAndMoodId(Long categoryId, Long moodId);
    List<Article> findByCategoryIdAndProblemId(Long categoryId, Long problemId);

    @Query(
            value = "SELECT * FROM sts_blog ORDER BY created_date DESC LIMIT 3",
            nativeQuery = true
    )
    List<Article> findLastThree();
}
