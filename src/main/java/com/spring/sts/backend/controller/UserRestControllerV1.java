package com.spring.sts.backend.controller;

import com.spring.sts.backend.entity.*;
import com.spring.sts.backend.service.ArticleService;
import com.spring.sts.backend.service.BlogService;
import com.spring.sts.backend.service.ImageArticleService;
import com.spring.sts.backend.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getBlogsStatusIsAccepted();
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/userblogs")
    public ResponseEntity<List<Blog>> getBlogsByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        List<Blog> blogs = blogService.getBlogsByUserId(user.getId());
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
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

    @GetMapping("blog/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Blog blog = blogService.getBlogById(id);
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blog, HttpStatus.OK);
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
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("lastArticles")
    public ResponseEntity<List<Article>> getLastThreeArticles() {
        List<Article> articles = articleService.getLastThree();
        HashMap<Long, HashMap<Article, ImageArticle>> result = new HashMap<Long, HashMap<Article, ImageArticle>>();
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Article article: articles) {
            ImageArticle imageArticle = imageArticleService.getImageArticleByArticleId(article.getId());
            if (imageArticle != null) {
                Map<Article, ImageArticle> articleMap = new HashMap<>();
                articleMap.put(article, imageArticle);
                result.put(article.getId(), null);
            }
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("article/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}")
    public ResponseEntity<List<Article>> getArticlesByCategoryId(@PathVariable Long categoryId) {
        if (categoryId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Article> articles = articleService.getArticlesByCategoryId(categoryId);
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}/mood/{moodId}/problem/{problemId}")
    public ResponseEntity<List<Article>> getArticlesByCategoryIdAndMoodIdAndProblemId(
            @PathVariable Long categoryId,
            @PathVariable Long moodId,
            @PathVariable Long problemId) {
        if (categoryId == null || moodId == null || problemId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Article> articles = articleService.getArticlesByCategoryIdAndMoodIdAndProblemId(categoryId,
                moodId, problemId);
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}/mood/{moodId}")
    public ResponseEntity<List<Article>> getArticlesByCategoryIdAndMoodId(
            @PathVariable Long categoryId,
            @PathVariable Long moodId) {
        if (categoryId == null || moodId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Article> articles = articleService.getArticlesByCategoryIdAndMoodId(categoryId, moodId);
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("articles/category/{categoryId}/problem/{problemId}")
    public ResponseEntity<List<Article>> getArticlesByCategoryIdAndProblemId(
            @PathVariable Long categoryId,
            @PathVariable Long problemId) {
        if (categoryId == null || problemId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Article> articles = articleService.getArticlesByCategoryIdAndProblemId(categoryId, problemId);
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

}
