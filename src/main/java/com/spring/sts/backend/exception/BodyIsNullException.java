package com.spring.sts.backend.exception;

public class BodyIsNullException extends RuntimeException {

    public BodyIsNullException(String field) {
        super(field + " is Null!");
    }

    public BodyIsNullException(String object, String fields) {
        super("One or more fields(" + fields + ") of " + object + " is/are Null!");
    }

}
