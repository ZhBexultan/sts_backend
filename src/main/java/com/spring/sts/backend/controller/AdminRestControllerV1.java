package com.spring.sts.backend.controller;

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
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(user, userFromDB, "id");
        userService.saveUser(userFromDB);
        return new ResponseEntity<>(userFromDB, HttpStatus.OK);
    }


    /****************************************  USER SERVICE  ****************************************/
    @PostMapping("user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.register(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
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
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(user, userFromDB, "id");
        userService.saveUser(userFromDB);
        return new ResponseEntity<>(userFromDB, HttpStatus.OK);
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
        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PostMapping("article")
    public ResponseEntity<Article> addArticle(@RequestBody Article article,
                                              HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        article.setUser(user);
        article.setBlog(false);
        article.setCreatedDate(LocalDateTime.now());
        articleService.saveArticle(article);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
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
        articleFromDB = articleService.getArticleById(articleFromDB.getId());
        User user = articleFromDB.getUser();
        if (article == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(article, articleFromDB, "id");
        articleFromDB.setUser(user);
        articleFromDB.setUpdatedDate(LocalDateTime.now());
        articleService.saveArticle(articleFromDB);
        return new ResponseEntity<>(articleFromDB, HttpStatus.OK);
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

    /****************************************  BLOG SERVICE  ****************************************/
    @GetMapping("blogs")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
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
        blog.setBlog(true);
        blog.setStatus(Status.ACCEPTED);
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
                                                 @RequestBody Blog blog) {
        blogFromDB = blogService.getBlogById(blogFromDB.getId());
        User user = blogFromDB.getUser();
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(blog, blogFromDB, "id");
        blogFromDB.setUser(user);
        blogFromDB.setUpdatedDate(LocalDateTime.now());
        blogService.saveBlog(blogFromDB);
        return new ResponseEntity<>(blogFromDB, HttpStatus.OK);
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


    /****************************************  IMAGE ARTICLE SERVICE  ****************************************/
    @PostMapping("imageArticle")
    public ResponseEntity<ImageArticle> addImageArticle(@RequestBody ImageArticle imageArticle) {
        if (imageArticle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        imageArticleService.saveImageArticle(imageArticle);
        return new ResponseEntity<>(imageArticle, HttpStatus.CREATED);
    }

    @GetMapping("imageArticle/{id}")
    public ResponseEntity<ImageArticle> getImageArticleById(@PathVariable Long id) {
        ImageArticle imageArticle = imageArticleService.getImageArticleById(id);
        if (imageArticle == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(imageArticle, HttpStatus.OK);
    }

    @GetMapping("imageArticles")
    public ResponseEntity<List<ImageArticle>> getAllImageArticles() {
        List<ImageArticle> imageArticles = imageArticleService.getAllImageArticles();
        if (imageArticles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(imageArticles, HttpStatus.OK);
    }

    @DeleteMapping("imageArticle/{id}")
    public ResponseEntity<ImageArticle> deleteImageArticle(@PathVariable Long id) {
        ImageArticle imageArticle = imageArticleService.getImageArticleById(id);
        if (imageArticle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        imageArticleService.deleteImageArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("imageArticle/{id}")
    public ResponseEntity<ImageArticle> updateImageArticle(@PathVariable("id") ImageArticle imageArticleFromDB,
                                         @RequestBody ImageArticle imageArticle) {
        if (imageArticle == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(imageArticle, imageArticleFromDB, "id");
        imageArticleService.saveImageArticle(imageArticleFromDB);
        return new ResponseEntity<>(imageArticleFromDB, HttpStatus.OK);
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
}
