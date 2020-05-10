package com.spring.sts.backend.controller;

import com.spring.sts.backend.dto.*;
import com.spring.sts.backend.entity.*;
import com.spring.sts.backend.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/user/")
public class UserRestControllerV1 {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ImageArticleService imageArticleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ImageBlogService imageBlogService;

    /****************************************  USER SERVICE  ****************************************/
    @GetMapping("/")
    public ResponseEntity<User> getUserById(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<User> deleteUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(user.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUser(HttpServletRequest request,
                                           @RequestBody User user) {
        HttpSession session = request.getSession();
        User userFromDB = (User) session.getAttribute("current_user");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(user, userFromDB, "id");
        userService.saveUser(userFromDB);
        return new ResponseEntity<>(userFromDB, HttpStatus.OK);
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

    @GetMapping("/userblogs")
    public ResponseEntity getBlogsByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        List<Blog> blogs = blogService.getBlogsByUserId(user.getId());
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

    @PostMapping("blog")
    public ResponseEntity<Blog> addBlog(@RequestBody Blog blog,
                                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blog.setUser(user);
        blog.setCreatedDate(LocalDateTime.now());
        blog.setStatus(Status.CREATED);
        blog.setBlog(true);
        blogService.saveBlog(blog);
        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }

    @DeleteMapping("blog/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable Long id) {
        Blog blog = blogService.getBlogById(id);
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blogService.deleteBlog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("blog/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable("id") Blog blogFromDB,
                                           @RequestBody Blog blog,
                                           HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(blog, blogFromDB, "id");
        blogFromDB.setUser(user);
        blogFromDB.setUpdatedDate(LocalDateTime.now());
        blogFromDB.setStatus(Status.CREATED);
        blogService.saveBlog(blogFromDB);
        return new ResponseEntity<>(blogFromDB, HttpStatus.OK);
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


    /****************************************  IMAGE BLOG SERVICE  ****************************************/
    @PostMapping("imageBlog")
    public ResponseEntity<ImageBlog> addImageBlog(@RequestBody ImageBlog imageBlog) {
        if (imageBlog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        imageBlogService.saveImageBlog(imageBlog);
        return new ResponseEntity<>(imageBlog, HttpStatus.CREATED);
    }

    @GetMapping("imageBlog/{id}")
    public ResponseEntity<ImageBlog> getImageBlogById(@PathVariable Long id) {
        ImageBlog imageBlog = imageBlogService.getImageBlogById(id);
        if (imageBlog == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(imageBlog, HttpStatus.OK);
    }

    @GetMapping("imageBlogs")
    public ResponseEntity<List<ImageBlog>> getAllImageBlogs() {
        List<ImageBlog> imageBlogs = imageBlogService.getAllImageBlogs();
        if (imageBlogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(imageBlogs, HttpStatus.OK);
    }

    @DeleteMapping("imageBlog/{id}")
    public ResponseEntity<ImageBlog> deleteImageBlog(@PathVariable Long id) {
        ImageBlog imageBlog = imageBlogService.getImageBlogById(id);
        if (imageBlog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        imageBlogService.deleteImageBlog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("imageBlog/{id}")
    public ResponseEntity<ImageBlog> updateImageBlog(@PathVariable("id") ImageBlog imageBlogFromDB,
                                                     @RequestBody ImageBlog imageBlog) {
        if (imageBlog == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(imageBlog, imageBlogFromDB, "id");
        imageBlogService.saveImageBlog(imageBlogFromDB);
        return new ResponseEntity<>(imageBlogFromDB, HttpStatus.OK);
    }


    /****************************************  TAG SERVICE  ****************************************/
    @PostMapping("tag")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tagService.saveTag(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @GetMapping("tag/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping("tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        if (tags.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @DeleteMapping("tag/{id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("tag/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") Tag tagFromDB,
                                         @RequestBody Tag tag) {
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(tag, tagFromDB, "id");
        tagService.saveTag(tagFromDB);
        return new ResponseEntity<>(tagFromDB, HttpStatus.OK);
    }

}
