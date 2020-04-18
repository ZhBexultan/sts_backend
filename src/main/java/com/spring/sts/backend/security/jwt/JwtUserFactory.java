package com.spring.sts.backend.security.jwt;

import com.spring.sts.backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getImageUrl(),
                Collections.singleton(user.getRole()));
    }

}
