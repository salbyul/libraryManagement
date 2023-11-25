package com.example.libraryManagement.book.controller;

import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.service.BookService;
import com.example.libraryManagement.common.response.ApiResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Builder
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ApiResponse register(@RequestBody final BookRegisterRequest bookRegisterRequest) {
        log.info("{}", bookRegisterRequest);
        Long savedId = bookService.register(bookRegisterRequest);
        return ApiResponse.generate()
                .put("id", savedId);
    }
}
