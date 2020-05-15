package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.Article;
import com.spring.sts.backend.exception.BodyIsNullException;
import com.spring.sts.backend.exception.NoReturnDataException;
import com.spring.sts.backend.exception.ObjectNotFoundException;
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
        if (article.getTitle() == null
                || article.getContent() == null
                || article.getShortContent() == null
                || article.getCategoryId() == null
                || article.getProblemId() == null
                || article.getUser() == null) {
            throw new BodyIsNullException("Article", "title, content, shortContent, categoryId, problemId");
        }
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
        if (id == null) {
            throw new BodyIsNullException("Article ID");
        }
        Article article = articleRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Article", id));
        log.info("IN ArticleServiceImpl getArticleById - article: {} found by id: {}", article, id);
        return article;
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            throw new NoReturnDataException("Articles");
        }
        log.info("IN ArticleServiceImpl getAllArticles - {} articles found", articles.size());
        return articles;
    }

    @Override
    public List<Article> getLastThree() {
        List<Article> articles = articleRepository.findLastThree();
        if (articles.isEmpty()) {
            throw new NoReturnDataException("Last added Articles");
        }
        log.info("IN ArticleServiceImpl getLastThree - {} articles found", articles.size());
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategoryId(Long categoryId) {
        List<Article> articles = articleRepository.findByCategoryId(categoryId);
        if (articles.isEmpty()) {
            throw new NoReturnDataException("Articles with Category ID: " + categoryId);
        }
        log.info("IN ArticleServiceImpl getArticlesByCategoryId - {} articles found", articles.size());
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategoryIdAndMoodIdAndProblemId(Long categoryId, Long moodId, Long problemId) {
        List<Article> articles = articleRepository.findByCategoryIdAndAndMoodIdAndProblemId(categoryId, moodId, problemId);
        if (articles.isEmpty()) {
            throw new NoReturnDataException("Articles with Category ID: " + categoryId
                    + " Mood ID: " + moodId
                    + " Problem ID: " + problemId);
        }
        log.info("IN ArticleServiceImpl getArticlesByCategoryIdAndMoodIdAndProblemId - {} articles found", articles.size());
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategoryIdAndMoodId(Long categoryId, Long moodId) {
        List<Article> articles = articleRepository.findByCategoryIdAndMoodId(categoryId, moodId);
        if (articles.isEmpty()) {
            throw new NoReturnDataException("Articles with Category ID: " + categoryId
                    + " Mood ID: " + moodId);
        }
        log.info("IN ArticleServiceImpl getArticlesByCategoryIdAndMoodId - {} articles found", articles.size());
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategoryIdAndProblemId(Long categoryId, Long problemId) {
        List<Article> articles = articleRepository.findByCategoryIdAndProblemId(categoryId, problemId);
        if (articles.isEmpty()) {
            throw new NoReturnDataException("Articles with Category ID: " + categoryId
                    + " Problem ID: " + problemId);
        }
        log.info("IN ArticleServiceImpl getArticlesByCategoryIdAndProblemId - {} articles found", articles.size());
        return articles;
    }

}
