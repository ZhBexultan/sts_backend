package com.spring.sts.backend.repository;

import com.spring.sts.backend.entity.ImageArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public interface ImageArticleRepository extends JpaRepository<ImageArticle, Long> {

    ImageArticle findFirstByArticle_Id(Long articleId);

    List<ImageArticle> findByArticle_Id(Long articleId);

}
