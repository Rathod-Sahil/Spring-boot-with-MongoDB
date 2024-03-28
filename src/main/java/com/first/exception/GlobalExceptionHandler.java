package com.first.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentIsAlreadyExisted.class)
    public ResponseEntity<ProblemDetail> StudentAlreadyExisted(StudentIsAlreadyExisted exception){
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pd.setTitle(exception.getMessage());
        pd.setStatus(HttpStatus.CONFLICT);
        pd.setDetail("Student with given email is already existed");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(pd);
    }

    @ExceptionHandler(StudentIsNotExisted.class)
    public ResponseEntity<ProblemDetail> StudentNotExisted(StudentIsNotExisted exception){
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle(exception.getMessage());
        pd.setStatus(HttpStatus.NOT_FOUND);
        pd.setDetail("Student with given email is not existed");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

}
