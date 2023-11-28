package com.example.libraryManagement.book.controller.advice;

import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.common.response.error.ErrorResponse;
import com.example.libraryManagement.common.response.error.ErrorType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.example.libraryManagement.book")
public class BookControllerAdvice {

    private static final String BOOK = "BOOK";

    @ExceptionHandler(BookException.class)
    public ErrorResponse handleBookException(final BookException e) {
        return ErrorResponse.generate(BOOK, e.getErrorType());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGlobalException(final Exception e) {
        e.printStackTrace();
        return ErrorResponse.generateAboutGlobal(ErrorType.UNKNOWN_ERROR);
    }
}
