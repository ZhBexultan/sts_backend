package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.ImageArticle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageArticleService {

    ImageArticle getImageArticleByArticleId(Long articleId);

    ImageArticle saveImageArticle(ImageArticle imageArticle);

    void deleteImageArticle(Long id);

    ImageArticle getImageArticleById(Long id);

    List<ImageArticle> getAllImageArticles();

    List<ImageArticle> getImageArticlesByArticleId(Long articleId);

}
