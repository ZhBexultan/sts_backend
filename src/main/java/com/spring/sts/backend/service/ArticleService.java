package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    Article saveArticle(Article article);

    void deleteArticle(Long id);

    Article getArticleById(Long id);

    List<Article> getArticlesByCategoryId(Long categoryId);

    List<Article> getArticlesByCategoryIdAndMoodIdAndProblemId(Long categoryId,
                                                               Long moodId,
                                                               Long problemId);

    List<Article> getArticlesByCategoryIdAndMoodId(Long categoryId,
                                                   Long moodId);

    List<Article> getArticlesByCategoryIdAndProblemId(Long categoryId,
                                                   Long problemId);

    List<Article> getAllArticles();

    List<Article> getLastThree();

}
