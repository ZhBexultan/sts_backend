package com.spring.sts.backend.service;

import com.spring.sts.backend.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User register(User user);

    List<User> getAllUsers();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);

    User saveUser(User user);

    String getRandomModerator();

}
