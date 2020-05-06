package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.ImageArticle;
import org.springframework.stereotype.Service;

@Service
public interface ImageArticleService {
    ImageArticle getImageArticleByArticleId(Long articleId);
}
