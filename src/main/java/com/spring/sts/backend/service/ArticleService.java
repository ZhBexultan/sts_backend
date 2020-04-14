package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.Article;

import java.util.List;

public interface ArticleService {

    Article saveArticle(Article article);

    void deleteArticle(Long id);

    Article getArticleById(Long id);

    List<Article> getArticlesByCategoryId(Long categoryId);

    List<Article> getArticlesByCategoryIdAndMoodIdAndProblemId(Long categoryId,
                                                               Long moodId,
                                                               Long problemId);

    List<Article> getAllArticles();

}
