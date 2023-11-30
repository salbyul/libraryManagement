package com.example.libraryManagement.book.controller;

import com.example.libraryManagement.book.dto.request.BookModificationRequest;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.book.service.BookService;
import com.example.libraryManagement.common.response.ApiResponse;
import com.example.libraryManagement.common.response.error.ErrorType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.example.libraryManagement.common.response.error.ErrorType.*;

@Slf4j
@RestController
@Builder
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final String LIBRARY_REGISTRANT_EMAIL = "Library-Registrant-Email";
    private final String LIBRARY_LEND_EMAIL = "Library-Lend-Email";
    private final String LIBRARY_RETURN_EMAIL = "Library-Return-Email";

    private final BookService bookService;

    /**
     * 도서 등록 메서드
     * 유저의 이메일과 등록할 도서의 정보를 이용해 도서를 등록합니다.
     *
     * @param registrantEmail 유저의 이메일
     * @param bookRegisterRequest 도서의 정보
     * @return 저장된 도서의 PK
     */
    @PostMapping
    public ApiResponse register(@RequestHeader(name = LIBRARY_REGISTRANT_EMAIL, required = false) final String registrantEmail,
                                @RequestBody final BookRegisterRequest bookRegisterRequest) {
        validateHeader(registrantEmail, HEADER_REGISTRANT_EMAIL_NULL);

        log.info("{}", bookRegisterRequest);
        Long savedId = bookService.register(registrantEmail, bookRegisterRequest);
        return ApiResponse.generate()
                .put("id", savedId);
    }

    /**
     * 도서 수정 메서드
     * 등록된 도서를 수정합니다.
     *
     * @param bookId 수정될 도서의 PK
     * @param bookModificationRequest 수정될 정보
     * @param registrantEmail 도서의 등록자인지 확인하기 위한 유저의 이메일
     * @return 없음
     */
    @PutMapping("/{id}")
    public ApiResponse modify(@PathVariable(name = "id") final Long bookId,
                              @RequestBody final BookModificationRequest bookModificationRequest,
                              @RequestHeader(name = LIBRARY_REGISTRANT_EMAIL, required = false) String registrantEmail) {
        validateHeader(registrantEmail, HEADER_REGISTRANT_EMAIL_NULL);

        log.info("{}", bookModificationRequest);
        bookService.modify(bookId, registrantEmail, bookModificationRequest);
        return ApiResponse.generate();
    }

    /**
     * 도서 대출 메서드
     * 특정 도서를 유저의 이메일을 이용해 대출합니다.
     *
     * @param bookId 대출할 책의 PK
     * @param email 대출하는 유저의 이메일
     * @return 없음
     */
    @PostMapping("/lend/{id}")
    public ApiResponse lend(@PathVariable(name = "id") final Long bookId,
                            @RequestHeader(value = LIBRARY_LEND_EMAIL, required = false) final String email) {
        validateHeader(email, HEADER_LEND_EMAIL_NULL);

        log.info("lent bookId: {}", bookId);
        bookService.lend(email, bookId);
        return ApiResponse.generate();
    }

    /**
     * 도서 반납 메서드
     * 특정 도서를 반납합니다.
     *
     * @param bookId 반납할 도서의 PK
     * @param email 대출한 유저인지 확인하기 위한 유저의 이메일
     * @return 없음
     */
    @PostMapping("/return/{id}")
    public ApiResponse returnBook(@PathVariable(name = "id") final Long bookId,
                                  @RequestHeader(value = LIBRARY_RETURN_EMAIL, required = false) final String email) {
        validateHeader(email, HEADER_RETURN_EMAIL_NULL);

        log.info("return bookId: {}", bookId);
        bookService.returnBook(email, bookId);
        return ApiResponse.generate();
    }

    private void validateHeader(final String value, final ErrorType errorType) {
        if (Objects.isNull(value)) {
            throw new BookException(errorType);
        }
    }
}