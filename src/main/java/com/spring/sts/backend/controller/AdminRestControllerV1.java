package com.spring.sts.backend.controller;

import com.spring.sts.backend.dto.UserDto;
import com.spring.sts.backend.entity.Article;
import com.spring.sts.backend.entity.Blog;
import com.spring.sts.backend.entity.User;
import com.spring.sts.backend.service.ArticleService;
import com.spring.sts.backend.service.BlogService;
import com.spring.sts.backend.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

    private final UserService userService;
    private final ArticleService articleService;
    private final BlogService blogService;

    @Autowired
    public AdminRestControllerV1(UserService userService, ArticleService articleService, BlogService blogService) {
        this.userService = userService;
        this.articleService = articleService;
        this.blogService = blogService;
    }

    /****************************************  USER SERVICE  ****************************************/
    @PostMapping("user/")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.register(user);
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UserDto> result = users.stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") User userFromDB,
                                                 @RequestBody User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(user, userFromDB, "id");
        userService.saveUser(userFromDB);
        return new ResponseEntity<>(userFromDB, httpHeaders, HttpStatus.OK);
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

    @PostMapping("article/")
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        articleService.saveArticle(article);
        return new ResponseEntity<>(article, httpHeaders, HttpStatus.CREATED);
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

    @DeleteMapping("article/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("article/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") Article articleFromDB,
                                 @RequestBody Article article) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (article == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(article, articleFromDB, "id");
        articleService.saveArticle(articleFromDB);
        return new ResponseEntity<>(articleFromDB, httpHeaders, HttpStatus.OK);
    }

    /****************************************  BLOG SERVICE  ****************************************/
    @GetMapping("blogs")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @PostMapping("blog/")
    public ResponseEntity<Blog> addBlog(@RequestBody Blog blog) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blogService.saveBlog(blog);
        return new ResponseEntity<>(blog, httpHeaders, HttpStatus.CREATED);
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
                                                 @RequestBody Blog blog) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(blog, blogFromDB, "id");
        blogService.saveBlog(blogFromDB);
        return new ResponseEntity<>(blogFromDB, httpHeaders, HttpStatus.OK);
    }

}
