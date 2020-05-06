package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.ImageArticle;
import com.spring.sts.backend.repository.ImageArticleRepository;
import com.spring.sts.backend.service.ImageArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageArticleServiceImpl implements ImageArticleService {

    @Autowired
    private ImageArticleRepository imageArticleRepository;

    @Override
    public ImageArticle getImageArticleByArticleId(Long articleId) {
        ImageArticle imageArticle = imageArticleRepository.findFirstByArticle_Id(articleId);
        if (imageArticle == null) {
            log.warn("IN ImageArticleServiceImpl getImageArticleByArticleId - no imageArticle found by id: {}", articleId);
            return null;
        }
        log.info("IN ImageArticleServiceImpl getImageArticleByArticleId - imageArticle: {} found by id: {}", imageArticle, articleId);
        return imageArticle;
    }

}
