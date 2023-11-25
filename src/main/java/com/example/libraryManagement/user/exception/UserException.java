package com.example.libraryManagement.user.exception;

import com.example.libraryManagement.common.exception.DomainException;
import com.example.libraryManagement.common.response.error.ErrorType;

public class UserException extends DomainException {

    public UserException(final ErrorType errorType) {
        super(errorType);
    }
}
