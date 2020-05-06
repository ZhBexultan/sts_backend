package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.Article;
import com.spring.sts.backend.repository.ArticleRepository;
import com.spring.sts.backend.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article saveArticle(Article article) {
        Article savedArticle = articleRepository.save(article);
        log.info("IN ArticleServiceImpl saveArticle - article: {} successfully saved", savedArticle);
        return savedArticle;
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
        log.info("IN ArticleServiceImpl deleteArticle - article with id: {} successfully deleted", id);
    }

    @Override
    public Article getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            log.warn("IN ArticleServiceImpl getArticleById - no article found by id: {}", id);
            return null;
        }
        log.info("IN ArticleServiceImpl getArticleById - article: {} found by id: {}", article, id);
        return article;
    }

    @Override
    public List<Article> getArticlesByCategoryId(Long categoryId) {
        List<Article> articles = articleRepository.findByCategoryId(categoryId);
        log.info("IN ArticleServiceImpl getArticlesByCategoryId - {} articles found", articles.size());
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategoryIdAndMoodIdAndProblemId(Long categoryId, Long moodId, Long problemId) {
        List<Article> articles = articleRepository.findByCategoryIdAndAndMoodIdAndProblemId(categoryId, moodId, problemId);
        log.info("IN ArticleServiceImpl getArticlesByCategoryIdAndMoodIdAndProblemId - {} articles found", articles.size());
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategoryIdAndMoodId(Long categoryId, Long moodId) {
        List<Article> articles = articleRepository.findByCategoryIdAndMoodId(categoryId, moodId);
        log.info("IN ArticleServiceImpl getArticlesByCategoryIdAndMoodId - {} articles found", articles.size());
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategoryIdAndProblemId(Long categoryId, Long problemId) {
        List<Article> articles = articleRepository.findByCategoryIdAndProblemId(categoryId, problemId);
        log.info("IN ArticleServiceImpl getArticlesByCategoryIdAndProblemId - {} articles found", articles.size());
        return articles;
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        log.info("IN ArticleServiceImpl getAllArticles - {} articles found", articles.size());
        return articles;
    }

    @Override
    public List<Article> getLastThree() {
        List<Article> articles = articleRepository.findLastThree();
        log.info("IN ArticleServiceImpl getLastThree - {} articles found", articles.size());
        return articles;
    }
}
