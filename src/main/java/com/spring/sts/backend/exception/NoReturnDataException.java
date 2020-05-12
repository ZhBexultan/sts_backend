package com.spring.sts.backend.exception;

public class NoReturnDataException extends RuntimeException {

    public NoReturnDataException(String objects) {
        super(objects + " is/are Empty!");
    }

}
