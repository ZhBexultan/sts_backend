package com.spring.sts.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.sts.backend.entity.Article;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleShortDto {

    private Long id;
    private Long categoryId;
    private Long moodId;
    private Long problemId;
    private String title;
    private String shortContent;
    private LocalDateTime createdDate;
    private ImageArticleDto image;

    public Article toArticle() {
        return new Article();
    }

    public static ArticleShortDto fromArticle(Article article, ImageArticleDto image) {
        ArticleShortDto articleShortDto = new ArticleShortDto();
        articleShortDto.setId(article.getId());
        articleShortDto.setCategoryId(article.getCategoryId());
        articleShortDto.setMoodId(article.getMoodId());
        articleShortDto.setProblemId(article.getProblemId());
        articleShortDto.setTitle(article.getTitle());
        articleShortDto.setShortContent(article.getShortContent());
        articleShortDto.setCreatedDate(article.getCreatedDate());
        articleShortDto.setImage(image);
        return  articleShortDto;
    }

}
