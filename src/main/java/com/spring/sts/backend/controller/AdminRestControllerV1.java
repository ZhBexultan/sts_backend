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
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ImageBlogService imageBlogService;

    @Autowired
    private ImageArticleService imageArticleService;

    @Autowired
    private FeedbackService feedbackService;

    /****************************************  ADMIN OWN SERVICE  ****************************************/
    @GetMapping("/")
    public ResponseEntity<User> getAdminById(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<User> deleteAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(user.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/")
    public ResponseEntity<User> updateAdmin(HttpServletRequest request,
                                           @RequestBody User user) {
        HttpSession session = request.getSession();
        User userFromDB = (User) session.getAttribute("current_user");
        BeanUtils.copyProperties(user, userFromDB, "id");
        userService.saveUser(userFromDB);
        return new ResponseEntity<>(userFromDB, HttpStatus.OK);
    }


    /****************************************  USER SERVICE  ****************************************/
    @PostMapping("user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.register(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        UserDto userDto = UserDto.fromUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usersDto = new ArrayList<>();
        List<User> users = userService.getAllUsers();
        for (User user: users) {
            usersDto.add(UserDto.fromUser(user));
        }
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") User userFromDB,
                                                 @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDB, "id");
        userService.saveUser(userFromDB);
        return new ResponseEntity<>(userFromDB, HttpStatus.OK);
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
        return new ResponseEntity<>(articleShortDtos, HttpStatus.OK);
    }

    @GetMapping("article/{id}")
    public ResponseEntity getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        List<ImageArticle> images = imageArticleService.getImageArticlesByArticleId(article.getId());
        ArticleDto articleDto = ArticleDto.fromArticle(article, images);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}")
    public ResponseEntity getArticlesByCategoryId(@PathVariable Long categoryId) {
        List<Article> articles = articleService.getArticlesByCategoryId(categoryId);
        List<ArticleShortDto> articleShortDtos = new ArrayList<>();
        for (Article article: articles) {
            ImageArticleDto firstImageArticleDto =
                    ImageArticleDto.fromImageArticle(imageArticleService.getImageArticleByArticleId(article.getId()));
            articleShortDtos.add(ArticleShortDto.fromArticle(article, firstImageArticleDto));
        }
        return new ResponseEntity<>(articleShortDtos, HttpStatus.OK);
    }

    @PostMapping("article")
    public ResponseEntity addArticle(@RequestBody Article article,
                                              HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        article.setUser(user);
        article.setBlog(false);
        article.setCreatedDate(LocalDateTime.now());
        articleService.saveArticle(article);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @DeleteMapping("article/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("article/{id}")
    public ResponseEntity updateArticle(@PathVariable("id") Article articleFromDB,
                                 @RequestBody Article article) {
        BeanUtils.copyProperties(article, articleFromDB, "id", "createdDate", "user");
        articleFromDB.setUpdatedDate(LocalDateTime.now());
        articleFromDB.setBlog(false);
        articleService.saveArticle(articleFromDB);
        List<ImageArticle> images = imageArticleService.getImageArticlesByArticleId(articleFromDB.getId());
        ArticleDto articleDto = ArticleDto.fromArticle(articleFromDB, images);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}/mood/{moodId}/problem/{problemId}")
    public ResponseEntity getArticlesByCategoryIdAndMoodIdAndProblemId(
            @PathVariable Long categoryId,
            @PathVariable Long moodId,
            @PathVariable Long problemId) {
        List<Article> articles = articleService.getArticlesByCategoryIdAndMoodIdAndProblemId(categoryId,
                moodId, problemId);
        List<ArticleShortDto> articleShortDtos = new ArrayList<>();
        for (Article article: articles) {
            ImageArticleDto firstImageArticleDto =
                    ImageArticleDto.fromImageArticle(imageArticleService.getImageArticleByArticleId(article.getId()));
            articleShortDtos.add(ArticleShortDto.fromArticle(article, firstImageArticleDto));
        }
        return new ResponseEntity<>(articleShortDtos, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}/mood/{moodId}")
    public ResponseEntity getArticlesByCategoryIdAndMoodId(
            @PathVariable Long categoryId,
            @PathVariable Long moodId) {
        List<Article> articles = articleService.getArticlesByCategoryIdAndMoodId(categoryId, moodId);
        List<ArticleShortDto> articleShortDtos = new ArrayList<>();
        for (Article article: articles) {
            ImageArticleDto firstImageArticleDto =
                    ImageArticleDto.fromImageArticle(imageArticleService.getImageArticleByArticleId(article.getId()));
            articleShortDtos.add(ArticleShortDto.fromArticle(article, firstImageArticleDto));
        }
        return new ResponseEntity<>(articleShortDtos, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}/problem/{problemId}")
    public ResponseEntity getArticlesByCategoryIdAndProblemId(
            @PathVariable Long categoryId,
            @PathVariable Long problemId) {
        List<Article> articles = articleService.getArticlesByCategoryIdAndProblemId(categoryId, problemId);
        List<ArticleShortDto> articleShortDtos = new ArrayList<>();
        for (Article article: articles) {
            ImageArticleDto firstImageArticleDto =
                    ImageArticleDto.fromImageArticle(imageArticleService.getImageArticleByArticleId(article.getId()));
            articleShortDtos.add(ArticleShortDto.fromArticle(article, firstImageArticleDto));
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
        return new ResponseEntity<>(blogShortDtos, HttpStatus.OK);
    }

    @GetMapping("blog/{id}")
    public ResponseEntity getBlogById(@PathVariable Long id,
                                      HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        Blog blog = blogService.getBlogById(id, user);
        List<ImageBlog> images = imageBlogService.getImageBlogsByBlogId(blog.getId());
        BlogDto blogDto = BlogDto.fromBlog(blog, images);
        return new ResponseEntity<>(blogDto, HttpStatus.OK);
    }

    @PostMapping("blog")
    public ResponseEntity addBlog(@RequestBody Blog blog,
                                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        blog.setUser(user);
        blog.setCreatedDate(LocalDateTime.now());
        blog.setBlog(true);
        blog.setStatus(Status.ACCEPTED);
        blogService.saveBlog(blog);
        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }

    @DeleteMapping("blog/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable Long id) {
        Blog blog = blogService.getBlogById(id);
        blogService.deleteBlog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("blog/{id}")
    public ResponseEntity updateBlog(@PathVariable("id") Blog blogFromDB,
                                                 @RequestBody Blog blog) {
        BeanUtils.copyProperties(blog, blogFromDB, "id", "createdDate", "user");
        blogFromDB.setUpdatedDate(LocalDateTime.now());
        blogFromDB.setBlog(true);
        blogService.saveBlog(blogFromDB);
        List<ImageBlog> images = imageBlogService.getImageBlogsByBlogId(blogFromDB.getId());
        BlogDto blogDto = BlogDto.fromBlog(blogFromDB, images);
        return new ResponseEntity<>(blogDto, HttpStatus.OK);
    }


    /****************************************  TAG SERVICE  ****************************************/
    @PostMapping("tag")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        tagService.saveTag(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @GetMapping("tag/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping("tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @DeleteMapping("tag/{id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("tag/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") Tag tagFromDB,
                                           @RequestBody Tag tag) {
        BeanUtils.copyProperties(tag, tagFromDB, "id");
        tagService.saveTag(tagFromDB);
        return new ResponseEntity<>(tagFromDB, HttpStatus.OK);
    }


    /****************************************  IMAGE ARTICLE SERVICE  ****************************************/
    @PostMapping("imageArticle/{articleId}")
    public ResponseEntity addImageArticle(@RequestBody ImageArticle imageArticle,
                                                        @PathVariable Long articleId) {
        Article article = articleService.getArticleById(articleId);
        imageArticle.setArticle(article);
        imageArticleService.saveImageArticle(imageArticle);
        ImageArticleDto imageArticleDto = ImageArticleDto.fromImageArticle(imageArticle);
        return new ResponseEntity<>(imageArticleDto, HttpStatus.CREATED);
    }

    @GetMapping("imageArticle/{id}")
    public ResponseEntity<ImageArticle> getImageArticleById(@PathVariable Long id) {
        ImageArticle imageArticle = imageArticleService.getImageArticleById(id);
        return new ResponseEntity<>(imageArticle, HttpStatus.OK);
    }

    @GetMapping("imageArticles")
    public ResponseEntity<List<ImageArticle>> getAllImageArticles() {
        List<ImageArticle> imageArticles = imageArticleService.getAllImageArticles();
        return new ResponseEntity<>(imageArticles, HttpStatus.OK);
    }

    @DeleteMapping("imageArticle/{id}")
    public ResponseEntity<ImageArticle> deleteImageArticle(@PathVariable Long id) {
        ImageArticle imageArticle = imageArticleService.getImageArticleById(id);
        imageArticleService.deleteImageArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("imageArticle/{id}")
    public ResponseEntity updateImageArticle(@PathVariable("id") ImageArticle imageArticleFromDB,
                                         @RequestBody ImageArticle imageArticle) {
        BeanUtils.copyProperties(imageArticle, imageArticleFromDB, "id", "article");
        imageArticleService.saveImageArticle(imageArticleFromDB);
        ImageArticleDto imageArticleDto = ImageArticleDto.fromImageArticle(imageArticleFromDB);
        return new ResponseEntity<>(imageArticleDto, HttpStatus.OK);
    }


    /****************************************  IMAGE BLOG SERVICE  ****************************************/
    @PostMapping("imageBlog/{blogId}")
    public ResponseEntity addImageBlog(@RequestBody ImageBlog imageBlog,
                                                  @PathVariable Long blogId) {
        Blog blog = blogService.getBlogById(blogId);
        imageBlog.setBlog(blog);
        imageBlogService.saveImageBlog(imageBlog);
        ImageBlogDto imageBlogDto = ImageBlogDto.fromImageBlog(imageBlog);
        return new ResponseEntity<>(imageBlogDto, HttpStatus.CREATED);
    }

    @GetMapping("imageBlog/{id}")
    public ResponseEntity<ImageBlog> getImageBlogById(@PathVariable Long id) {
        ImageBlog imageBlog = imageBlogService.getImageBlogById(id);
        return new ResponseEntity<>(imageBlog, HttpStatus.OK);
    }

    @GetMapping("imageBlogs")
    public ResponseEntity<List<ImageBlog>> getAllImageBlogs() {
        List<ImageBlog> imageBlogs = imageBlogService.getAllImageBlogs();
        return new ResponseEntity<>(imageBlogs, HttpStatus.OK);
    }

    @DeleteMapping("imageBlog/{id}")
    public ResponseEntity<ImageBlog> deleteImageBlog(@PathVariable Long id) {
        ImageBlog imageBlog = imageBlogService.getImageBlogById(id);
        imageBlogService.deleteImageBlog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("imageBlog/{id}")
    public ResponseEntity updateImageBlog(@PathVariable("id") ImageBlog imageBlogFromDB,
                                                           @RequestBody ImageBlog imageBlog) {
        BeanUtils.copyProperties(imageBlog, imageBlogFromDB, "id", "blog");
        imageBlogService.saveImageBlog(imageBlogFromDB);
        ImageBlogDto imageBlogDto = ImageBlogDto.fromImageBlog(imageBlogFromDB);
        return new ResponseEntity<>(imageBlogDto, HttpStatus.OK);
    }


    /****************************************  FEEDBACK SERVICE  ****************************************/
    @GetMapping("feedback/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Feedback feedback = feedbackService.findById(id);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    @GetMapping("feedbacks")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @DeleteMapping("feedback/{id}")
    public ResponseEntity<Feedback> deleteFeedback(@PathVariable Long id) {
        Feedback feedback = feedbackService.findById(id);
        feedbackService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
