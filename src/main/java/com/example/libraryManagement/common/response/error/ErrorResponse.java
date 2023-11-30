package com.example.libraryManagement.common.response.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    public static final String GLOBAL = "GLOBAL";
    private final String domain;
    private final String message;
    private final LocalDateTime dateTime;

    public static ErrorResponse generateAboutGlobal(final ErrorType errorType) {
        return new ErrorResponse(GLOBAL, errorType.getMessage(), LocalDateTime.now());
    }

    public static ErrorResponse generate(final String domain, final ErrorType errorType, final LocalDateTime dateTime) {
        return new ErrorResponse(domain, errorType.getMessage(), dateTime);
    }

}
