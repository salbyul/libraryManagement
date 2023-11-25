package com.example.libraryManagement.common.exception;

import com.example.libraryManagement.common.response.error.ErrorType;

public class GlobalException extends RuntimeException {

    protected final ErrorType errorType;

    public GlobalException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
