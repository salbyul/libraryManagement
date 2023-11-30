package com.example.libraryManagement.user.controller.advice;

import com.example.libraryManagement.common.response.error.ErrorResponse;
import com.example.libraryManagement.common.response.error.ErrorType;
import com.example.libraryManagement.user.exception.UserException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.example.libraryManagement.user")
public class UserControllerAdvice {

    private static final String USER = "USER";

    @ExceptionHandler(UserException.class)
    public ErrorResponse handleUserException(final UserException e) {
        return ErrorResponse.generate(USER, e.getErrorType(), e.getDateTime());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGlobalException(final Exception e) {
        e.printStackTrace();
        return ErrorResponse.generateAboutGlobal(ErrorType.UNKNOWN_ERROR);
    }
}
