package com.spring.sts.backend.controller;

import com.spring.sts.backend.dto.AuthenticationRequestDto;
import com.spring.sts.backend.entity.User;
import com.spring.sts.backend.security.jwt.JwtTokenProvider;
import com.spring.sts.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRole());
            HttpSession session = request.getSession();
            session.setAttribute("current_user", user);
            Cookie cookie = new Cookie("user_token", token);
            cookie.setMaxAge(3600000);
            cookie.setHttpOnly(false);
            response.addCookie(cookie);

            Map<Object, Object> result = new HashMap<>();
            String returnUrl = "/";
            if (user.getRole().getIndex()==1) {
                returnUrl = "admin.html";
            }
            result.put("username", username);
            result.put("token", token);
            result.put("user", user.getRole().getIndex());
            result.put("url", returnUrl);
            return ResponseEntity.ok(result);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping("logout")
    public ResponseEntity logout(HttpServletRequest request,
                                 HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c: cookies) {
                if (c.getName().equals("user_token")) {
                    c.setMaxAge(0);
                    response.addCookie(c);
                }
            }
        }
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        Map<Object, Object> result = new HashMap<>();
        result.put("url", "/");
        return ResponseEntity.ok(result);
    }
}
