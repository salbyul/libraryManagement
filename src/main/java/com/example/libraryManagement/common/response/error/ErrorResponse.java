package com.example.libraryManagement.common.response.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    public static final String GLOBAL = "GLOBAL";
    private final String domain;
    private final String message;

    public static ErrorResponse generateAboutGlobal(final ErrorType errorType) {
        return new ErrorResponse(GLOBAL, errorType.getMessage());
    }

    public static ErrorResponse generate(final String domain, final ErrorType errorType) {
        return new ErrorResponse(domain, errorType.getMessage());
    }

}
