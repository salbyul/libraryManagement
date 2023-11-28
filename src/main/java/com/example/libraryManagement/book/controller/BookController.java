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
    public ApiResponse register(@RequestBody final BookRegisterRequest bookRegisterRequest) { // FIXME: 2023/11/28 registrant Email 객체와 분리하기
        log.info("{}", bookRegisterRequest);
        Long savedId = bookService.register(bookRegisterRequest);
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
}
