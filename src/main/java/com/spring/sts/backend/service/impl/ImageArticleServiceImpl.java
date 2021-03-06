package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.ImageArticle;
import com.spring.sts.backend.exception.BodyIsNullException;
import com.spring.sts.backend.exception.ObjectNotFoundException;
import com.spring.sts.backend.repository.ImageArticleRepository;
import com.spring.sts.backend.service.ImageArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ImageArticleServiceImpl implements ImageArticleService {

    @Autowired
    private ImageArticleRepository imageArticleRepository;

    @Override
    public ImageArticle saveImageArticle(ImageArticle imageArticle) {
        if (imageArticle.getUrl() == null) {
            throw new BodyIsNullException("ImageArticle", "url");
        }
        ImageArticle savedImageArticle = imageArticleRepository.save(imageArticle);
        log.info("IN ImageArticleServiceImpl saveImageArticle - imageArticle: {} successfully saved", savedImageArticle);
        return savedImageArticle;
    }

    @Override
    public void deleteImageArticle(Long id) {
        imageArticleRepository.deleteById(id);
        log.info("IN ImageArticleServiceImpl deleteImageArticle - imageArticle with id: {} successfully deleted", id);
    }

    @Override
    public ImageArticle getImageArticleById(Long id) {
        ImageArticle imageArticle = imageArticleRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("ImageArticle", id));
        log.info("IN ImageArticleServiceImpl getImageArticleById - imageArticle: {} found by id: {}", imageArticle, id);
        return imageArticle;
    }

    @Override
    public ImageArticle getImageArticleByArticleId(Long articleId) {
        ImageArticle imageArticle = imageArticleRepository.findFirstByArticle_Id(articleId);
        log.info("IN ImageArticleServiceImpl getImageArticleByArticleId - imageArticle: {} found by id: {}", imageArticle, articleId);
        return imageArticle;
    }

    @Override
    public List<ImageArticle> getAllImageArticles() {
        List<ImageArticle> imageArticles = imageArticleRepository.findAll();
        log.info("IN ImageArticleServiceImpl getAllImageArticles - {} tags found", imageArticles.size());
        return imageArticles;
    }

    @Override
    public List<ImageArticle> getImageArticlesByArticleId(Long articleId) {
        List<ImageArticle> images = imageArticleRepository.findByArticle_Id(articleId);
        log.info("IN ImageArticleServiceImpl getImageArticlesByArticleId - {} images found", images.size());
        return images;
    }

}
