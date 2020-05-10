package com.spring.sts.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.sts.backend.entity.Article;
import com.spring.sts.backend.entity.ImageArticle;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto {

    private Long id;
    private String title;
    private String content;
    private String shortContent;
    private Long categoryId;
    private Long problemId;
    private LocalDateTime createdDate;
    private List<ImageArticleDto> images;

    public Article toArticle() {
        return new Article();
    }

    public static ArticleDto fromArticle(Article article, List<ImageArticle> images) {
        List<ImageArticleDto> imageArticleDtos = new ArrayList<>();
        for (ImageArticle imageArticle: images) {
            imageArticleDtos.add(ImageArticleDto.fromImageArticle(imageArticle));
        }
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());
        articleDto.setShortContent(article.getShortContent());
        articleDto.setCategoryId(article.getCategoryId());
        articleDto.setProblemId(article.getProblemId());
        articleDto.setCreatedDate(article.getCreatedDate());
        articleDto.setImages(imageArticleDtos);
        return  articleDto;
    }

}
