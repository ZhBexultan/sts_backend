package com.spring.sts.backend.service.impl;

import com.spring.sts.backend.entity.Role;
import com.spring.sts.backend.entity.User;
import com.spring.sts.backend.repository.UserRepository;
import com.spring.sts.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);
        log.info("IN UserServiceImpl register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        log.info("IN UserServiceImpl saveUser - user: {} successfully saved", savedUser);
        return savedUser;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("IN UserServiceImpl getAllUsers - {} users found", users.size());
        return users;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        log.info("IN UserServiceImpl findByUsername - user: {} found by username: {}", user, username);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("IN UserServiceImpl findById - no user found by id: {}", id);
            return null;
        }
        log.info("IN UserServiceImpl findById - user: {} found by id: {}", user, id);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN UserServiceImpl delete - user with id: {} successfully deleted", id);
    }
}
