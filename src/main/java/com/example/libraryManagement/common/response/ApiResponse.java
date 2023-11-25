package com.example.libraryManagement.common.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ApiResponse {

    private final LocalDateTime date;
    private final Map<String, Object> body;

    private ApiResponse() {
        this.date = LocalDateTime.now();
        this.body = new HashMap<>();
    }

    public static ApiResponse generate() {
        return new ApiResponse();
    }

    public ApiResponse put(final String key, final Object value) {
        body.put(key, value);
        return this;
    }
}
