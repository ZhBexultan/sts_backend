package com.spring.sts.backend.controller;

import com.spring.sts.backend.dto.*;
import com.spring.sts.backend.entity.*;
import com.spring.sts.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/unauthorized/")
public class UnauthorizedRestControllerV1 {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private ImageArticleService imageArticleService;

    @Autowired
    private ImageBlogService imageBlogService;

    /****************************************  USER SERVICE  ****************************************/
    @PostMapping("user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setFirstName("User@"+RandomizerUser.generate());
        user.setLastName("Anonymous");
        user.setRole(Role.ROLE_USER);
        userService.register(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /****************************************  ARTICLE SERVICE  ****************************************/
    @GetMapping("articles")
    public ResponseEntity getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        List<ArticleShortDto> articleShortDtos = new ArrayList<>();
        for (Article article: articles) {
            ImageArticleDto firstImageArticleDto =
                    ImageArticleDto.fromImageArticle(imageArticleService.getImageArticleByArticleId(article.getId()));
            articleShortDtos.add(ArticleShortDto.fromArticle(article, firstImageArticleDto));
        }
        if (articleShortDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleShortDtos, HttpStatus.OK);
    }

    @GetMapping("lastArticles")
    public ResponseEntity getLastThreeArticles() {
        List<Article> articles = articleService.getLastThree();
        List<ArticleShortDto> articleShortDtos = new ArrayList<>();
        for (Article article: articles) {
            ImageArticleDto firstImageArticleDto =
                    ImageArticleDto.fromImageArticle(imageArticleService.getImageArticleByArticleId(article.getId()));
            articleShortDtos.add(ArticleShortDto.fromArticle(article, firstImageArticleDto));
        }
        if (articleShortDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleShortDtos, HttpStatus.OK);
    }

    @GetMapping("article/{id}")
    public ResponseEntity getArticleById(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Article article = articleService.getArticleById(id);
        List<ImageArticle> images = imageArticleService.getImageArticlesByArticleId(article.getId());
        ArticleDto articleDto = ArticleDto.fromArticle(article, images);
        if (articleDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}")
    public ResponseEntity getArticlesByCategoryId(@PathVariable Long categoryId) {
        if (categoryId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Article> articles = articleService.getArticlesByCategoryId(categoryId);
        List<ArticleShortDto> articleShortDtos = new ArrayList<>();
        for (Article article: articles) {
            ImageArticleDto firstImageArticleDto =
                    ImageArticleDto.fromImageArticle(imageArticleService.getImageArticleByArticleId(article.getId()));
            articleShortDtos.add(ArticleShortDto.fromArticle(article, firstImageArticleDto));
        }
        if (articleShortDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleShortDtos, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}/mood/{moodId}/problem/{problemId}")
    public ResponseEntity getArticlesByCategoryIdAndMoodIdAndProblemId(
            @PathVariable Long categoryId,
            @PathVariable Long moodId,
            @PathVariable Long problemId) {
        if (categoryId == null || moodId == null || problemId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Article> articles = articleService.getArticlesByCategoryIdAndMoodIdAndProblemId(categoryId,
                moodId, problemId);
        List<ArticleShortDto> articleShortDtos = new ArrayList<>();
        for (Article article: articles) {
            ImageArticleDto firstImageArticleDto =
                    ImageArticleDto.fromImageArticle(imageArticleService.getImageArticleByArticleId(article.getId()));
            articleShortDtos.add(ArticleShortDto.fromArticle(article, firstImageArticleDto));
        }
        if (articleShortDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleShortDtos, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}/mood/{moodId}")
    public ResponseEntity getArticlesByCategoryIdAndMoodId(
            @PathVariable Long categoryId,
            @PathVariable Long moodId) {
        if (categoryId == null || moodId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Article> articles = articleService.getArticlesByCategoryIdAndMoodId(categoryId, moodId);
        List<ArticleShortDto> articleShortDtos = new ArrayList<>();
        for (Article article: articles) {
            ImageArticleDto firstImageArticleDto =
                    ImageArticleDto.fromImageArticle(imageArticleService.getImageArticleByArticleId(article.getId()));
            articleShortDtos.add(ArticleShortDto.fromArticle(article, firstImageArticleDto));
        }
        if (articleShortDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleShortDtos, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}/problem/{problemId}")
    public ResponseEntity getArticlesByCategoryIdAndProblemId(
            @PathVariable Long categoryId,
            @PathVariable Long problemId) {
        if (categoryId == null || problemId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Article> articles = articleService.getArticlesByCategoryIdAndProblemId(categoryId, problemId);
        List<ArticleShortDto> articleShortDtos = new ArrayList<>();
        for (Article article: articles) {
            ImageArticleDto firstImageArticleDto =
                    ImageArticleDto.fromImageArticle(imageArticleService.getImageArticleByArticleId(article.getId()));
            articleShortDtos.add(ArticleShortDto.fromArticle(article, firstImageArticleDto));
        }
        if (articleShortDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleShortDtos, HttpStatus.OK);
    }


    /****************************************  BLOG SERVICE  ****************************************/
    @GetMapping("blogs")
    public ResponseEntity getAllBlogs() {
        List<Blog> blogs = blogService.getBlogsStatusIsAccepted();
        List<BlogShortDto> blogShortDtos = new ArrayList<>();
        for (Blog blog: blogs) {
            ImageBlogDto firstImageBlogDto =
                    ImageBlogDto.fromImageBlog(imageBlogService.getImageBlogByBlogId(blog.getId()));
            blogShortDtos.add(BlogShortDto.fromBlog(blog, firstImageBlogDto));
        }
        if (blogShortDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blogShortDtos, HttpStatus.OK);
    }

    @GetMapping("blog/{id}")
    public ResponseEntity getBlogById(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Blog blog = blogService.getBlogById(id);
        List<ImageBlog> images = imageBlogService.getImageBlogsByBlogId(blog.getId());
        BlogDto blogDto = BlogDto.fromBlog(blog, images);
        if (blogDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blogDto, HttpStatus.OK);
    }

}
