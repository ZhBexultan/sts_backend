package com.spring.sts.backend.controller;

import com.spring.sts.backend.dto.BlogDto;
import com.spring.sts.backend.dto.BlogShortDto;
import com.spring.sts.backend.dto.ImageBlogDto;
import com.spring.sts.backend.entity.Blog;
import com.spring.sts.backend.entity.ImageBlog;
import com.spring.sts.backend.entity.User;
import com.spring.sts.backend.service.BlogService;
import com.spring.sts.backend.service.ImageBlogService;
import com.spring.sts.backend.service.UserService;
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
@RequestMapping(value = "/api/v1/moderator/")
public class ModeratorRestControllerV1 {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

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

    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user,
                                           HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromDB = (User) session.getAttribute("current_user");
        BeanUtils.copyProperties(user, userFromDB, "id");
        userService.saveUser(userFromDB);
        return new ResponseEntity<>(userFromDB, HttpStatus.OK);
    }


    /****************************************  BLOG SERVICE  ****************************************/
    @GetMapping("blogs")
    public ResponseEntity getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
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

}
