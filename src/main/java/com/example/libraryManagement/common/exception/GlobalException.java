package com.example.libraryManagement.common.exception;

import com.example.libraryManagement.common.response.error.ErrorType;

import java.time.LocalDateTime;

public class GlobalException extends RuntimeException {

    protected final ErrorType errorType;
    private final LocalDateTime dateTime;

    public GlobalException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.dateTime = LocalDateTime.now();
    }

    public final LocalDateTime getDateTime() {
        return this.dateTime;
    }
}
