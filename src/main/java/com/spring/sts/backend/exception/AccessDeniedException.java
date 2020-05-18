package com.spring.sts.backend.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super("Access denied!");
    }

}
