package com.first.exception;

import com.first.decorator.MessageConstants;
import com.first.decorator.DataResponse;
import com.first.decorator.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentIsAlreadyExisted.class)
    DataResponse<Object> studentAlreadyExisted(StudentIsAlreadyExisted exception) {
        return new DataResponse<>(null, Response.getConflictResponse(exception.getMessage()));
    }

    @ExceptionHandler(StudentIsNotExisted.class)
    DataResponse<Object> studentNotFound(StudentIsNotExisted exception) {
        return new DataResponse<>(null, Response.getNotFoundResponse(exception.getMessage()));
    }

    @ExceptionHandler(AdminCanNotBeDeleted.class)
    DataResponse<Object> adminCanNotBeDeletedExeption(AdminCanNotBeDeleted exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(StudentValidationException.class)
    DataResponse<Object> studentNotValidatated(StudentValidationException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(WrongPasswordException.class)
    DataResponse<Object> wrongPassword(WrongPasswordException exception) {
        return new DataResponse<>(null, Response.getUnauthorizedResponse(exception.getMessage()));
    }

    @ExceptionHandler(OTPNotMatchedException.class)
    DataResponse<Object> wrongOTP(OTPNotMatchedException exception) {
        return new DataResponse<>(null, Response.getUnauthorizedResponse(exception.getMessage()));
    }

    @ExceptionHandler(SamePasswordException.class)
    DataResponse<Object> sameNewPassword(SamePasswordException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    DataResponse<Object> passwordNotMatchedException(PasswordNotMatchException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(StudentBlockedException.class)
    DataResponse<Object> studentBlockedExeption(StudentBlockedException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage(), MessageConstants.BLOCKED));
    }

    @ExceptionHandler(StudentIsNotBlocked.class)
    DataResponse<Object> studentIsNotBlockedExeption(StudentIsNotBlocked exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(AdminCanNotBeBlocked.class)
    DataResponse<Object> adminCanNotBeBlockedExeption(AdminCanNotBeBlocked exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    DataResponse<Object> unauthorizedException(UnauthorizedException exception) {
        return new DataResponse<>(null, Response.getUnauthorizedResponse(exception.getMessage()));
    }

    @ExceptionHandler(NotAdminException.class)
    DataResponse<Object> notAdminException(NotAdminException exception) {
        return new DataResponse<>(null, Response.getUnauthorizedResponse(exception.getMessage()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    DataResponse<Object> tokenExpiredException(TokenExpiredException exception) {
        return new DataResponse<>(null, Response.getBadRequestResponse(exception.getMessage(), MessageConstants.TOKEN_EXPIRED));
    }

    @ExceptionHandler(Exception.class)
    DataResponse<Object> exception(Exception exception) {
        log.info("Error : {}", exception.getMessage(), exception);
        return new DataResponse<>(null, Response.getInternalServerErrorResponse(exception.getMessage()));
    }

}
