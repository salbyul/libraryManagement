package com.example.libraryManagement.book.exception;

import com.example.libraryManagement.common.exception.DomainException;
import com.example.libraryManagement.common.response.error.ErrorType;

public class BookException extends DomainException {

    public BookException(final ErrorType errorType) {
        super(errorType);
    }
}
