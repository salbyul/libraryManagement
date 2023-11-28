package com.example.libraryManagement.book.controller;

import com.example.libraryManagement.book.service.LentHistoryService;
import com.example.libraryManagement.common.response.ApiResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Builder
@RequiredArgsConstructor
@RequestMapping("/api/lent-history")
public class LentHistoryController {

    private final LentHistoryService lentHistoryService;

    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    @PostMapping
    public ApiResponse lend(final Long bookId, @RequestHeader("Library-Lend-Email") final String email) {
        Long savedId = lentHistoryService.lend(email, bookId);
        return ApiResponse.generate()
                .put("id", savedId);
    }
}
