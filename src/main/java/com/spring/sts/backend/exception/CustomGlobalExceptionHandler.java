package com.spring.sts.backend.exception;

import com.spring.sts.backend.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> springHandleNotFound(HttpServletResponse response,
                                                                    Exception e,
                                                                    WebRequest request) throws IOException {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(e.getMessage());
        errors.setStatus(status.value());
        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler(BodyIsNullException.class)
    public ResponseEntity<CustomErrorResponse> springHandleBodyIsNull(HttpServletResponse response,
                                                                    Exception e,
                                                                    WebRequest request) throws IOException {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(e.getMessage());
        errors.setStatus(status.value());
        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler(NoReturnDataException.class)
    public ResponseEntity<CustomErrorResponse> springHandleReturnDataIsEmpty(HttpServletResponse response,
                                                                      Exception e,
                                                                      WebRequest request) throws IOException {
        HttpStatus status = HttpStatus.NO_CONTENT;
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(e.getMessage());
        errors.setStatus(status.value());
        return new ResponseEntity<>(errors, status);
    }

}
