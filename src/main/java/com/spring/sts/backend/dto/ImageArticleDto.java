package com.spring.sts.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.sts.backend.entity.ImageArticle;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageArticleDto {

    private Long id;
    private String url;
    private String firebaseId;

    public ImageArticle toImageArticle() {
        return new ImageArticle();
    }

    public static ImageArticleDto fromImageArticle(ImageArticle imageArticle) {
        ImageArticleDto imageArticleDto = new ImageArticleDto();
        if (imageArticle != null) {
            imageArticleDto.setId(imageArticle.getId());
            imageArticleDto.setUrl(imageArticle.getUrl());
            imageArticleDto.setFirebaseId(imageArticle.getFirebaseId());
        }
        return  imageArticleDto;
    }

}
