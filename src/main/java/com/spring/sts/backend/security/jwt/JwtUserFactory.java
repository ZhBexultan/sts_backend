package com.spring.sts.backend.security.jwt;

import com.spring.sts.backend.entity.User;

import java.util.Collections;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                Collections.singleton(user.getRole()));
    }

}
