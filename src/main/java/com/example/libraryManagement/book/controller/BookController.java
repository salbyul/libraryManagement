package com.example.libraryManagement.book.controller;

import com.example.libraryManagement.book.dto.request.BookModificationRequest;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.service.BookService;
import com.example.libraryManagement.common.response.ApiResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Builder
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ApiResponse register(@RequestHeader(name = "Library-Registrant-Email") final String registrantEmail,
                                @RequestBody final BookRegisterRequest bookRegisterRequest) {
        log.info("{}", bookRegisterRequest);
        Long savedId = bookService.register(registrantEmail, bookRegisterRequest);
        return ApiResponse.generate()
                .put("id", savedId);
    }

    @PutMapping("/{id}")
    public ApiResponse modify(@PathVariable(name = "id") final Long bookId,
                              @RequestBody final BookModificationRequest bookModificationRequest,
                              @RequestHeader(name = "Library-Registrant-Email") String email) {
        log.info("{}", bookModificationRequest);
        bookService.modify(bookId, email, bookModificationRequest);
        return ApiResponse.generate();
    }

    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    @PostMapping("/lend/{id}")
    public ApiResponse lend(@PathVariable(name = "id") final Long bookId, @RequestHeader("Library-Lend-Email") final String email) {
        bookService.lend(email, bookId);
        return ApiResponse.generate();
    }

    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    @PostMapping("/return/{id}")
    public ApiResponse returnBook(@PathVariable(name = "id") final Long bookId, @RequestHeader("Library-Return-Email") final String email) {
        bookService.returnBook(email, bookId);
        return ApiResponse.generate();
    }
}
