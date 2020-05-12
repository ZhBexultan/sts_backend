package com.spring.sts.backend.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String object,Long id) {
        super(object + " with ID: {" + id + "} not found!");
    }

    public ObjectNotFoundException(String object, String field) {
        super(object + " with " + field + " not found!");
    }

}
