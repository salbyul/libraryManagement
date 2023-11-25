package com.example.libraryManagement.common.exception;

import com.example.libraryManagement.common.response.error.ErrorType;

public class DomainException extends GlobalException {

    public DomainException(final ErrorType errorType) {
        super(errorType);
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }
}
