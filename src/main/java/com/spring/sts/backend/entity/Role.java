package com.spring.sts.backend.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_MODERATOR, ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }

    public int getIndex() {
        return ordinal()+1;
    }
}
