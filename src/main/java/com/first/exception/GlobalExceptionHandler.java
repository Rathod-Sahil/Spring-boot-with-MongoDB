package com.first.exception;

import com.first.constant.MessageConstants;
import com.first.decorator.DataResponse;
import com.first.helper.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentExistedException.class)
    DataResponse<Object> studentExistedException(StudentExistedException exception) {
        return new DataResponse<>(null, Response.getConflictResponse(exception.getMessage()));
    }

    @ExceptionHandler(StudentNotExistedException.class)
    DataResponse<Object> studentNotExistedException(StudentNotExistedException exception) {
        return new DataResponse<>(null, Response.getNotFoundResponse(exception.getMessage()));
    }

    @ExceptionHandler(AdminDeletionException.class)
    DataResponse<Object> adminDeletionException(AdminDeletionException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(StudentValidationException.class)
    DataResponse<Object> studentValidationException(StudentValidationException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(WrongPasswordException.class)
    DataResponse<Object> wrongPasswordException(WrongPasswordException exception) {
        return new DataResponse<>(null, Response.getUnauthorizedResponse(exception.getMessage()));
    }

    @ExceptionHandler(WrongOTPException.class)
    DataResponse<Object> wrongOTPException(WrongOTPException exception) {
        return new DataResponse<>(null, Response.getUnauthorizedResponse(exception.getMessage()));
    }

    @ExceptionHandler(SamePasswordException.class)
    DataResponse<Object> samePasswordException(SamePasswordException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(StudentBlockedException.class)
    DataResponse<Object> studentBlockedException(StudentBlockedException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage(), MessageConstants.BLOCKED));
    }

    @ExceptionHandler(StudentNotBlockedException.class)
    DataResponse<Object> studentNotBlockedException(StudentNotBlockedException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(AdminBlockException.class)
    DataResponse<Object> adminBlockException(AdminBlockException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    DataResponse<Object> unauthorizedException(UnauthorizedException exception) {
        return new DataResponse<>(null, Response.getUnauthorizedResponse(exception.getMessage()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    DataResponse<Object> tokenExpiredException(TokenExpiredException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage(), MessageConstants.TOKEN_EXPIRED));
    }

    @ExceptionHandler(FileNotFoundException.class)
    DataResponse<Object> fileNotFoundException(FileNotFoundException exception) {
        return new DataResponse<>(null, Response.getNotFoundResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    DataResponse<Object> exception(Exception exception) {
        log.info("Error : {}", exception.getMessage(), exception);
        return new DataResponse<>(null, Response.getInternalServerErrorResponse(exception.getMessage()));
    }

}
