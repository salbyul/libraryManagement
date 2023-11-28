package com.example.libraryManagement.book.service;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.dto.request.BookModificationRequest;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.mock.FakeContainer;
import com.example.libraryManagement.user.dto.request.UserRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.example.libraryManagement.common.response.error.ErrorType.*;
import static org.assertj.core.api.Assertions.*;

class BookServiceTest {

    @Test
    @DisplayName("도서 등록 성공")
    void register() {
        FakeContainer fakeContainer = new FakeContainer();

        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .email("a@a.com")
                .build();
        fakeContainer.userService.register(userRegisterRequest);

        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .name("너에게 하고 싶은 말")
                .isbn("9791191043235")
                .build();

        fakeContainer.bookService.register(userRegisterRequest.getEmail(), bookRegisterRequest);
    }

    @Test
    @DisplayName("도서 등록 시 존재하지 않는 유저로 인한 예외 발생")
    void registerFailedByNotExistUser() {
        FakeContainer fakeContainer = new FakeContainer();

        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .name("너에게 하고 싶은 말")
                .isbn("9791191043235")
                .build();

        assertThatThrownBy(() ->
                fakeContainer.bookService.register("a@a.com", bookRegisterRequest)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("성공적인 도서 수정")
    void modify() {
        FakeContainer fakeContainer = new FakeContainer();

//        유저 생성
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .email("a@a.com")
                .build();
        fakeContainer.userService.register(userRegisterRequest);

//        도서 등록
        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .name("너에게 하고 싶은 말")
                .isbn("9791191043235")
                .build();
        Long savedBookId = fakeContainer.bookService.register(userRegisterRequest.getEmail(), bookRegisterRequest);

//        도서 수정
        BookModificationRequest bookModificationRequest = BookModificationRequest.builder()
                .name("너에게 하고 싶은 말2")
                .isbn("10000")
                .build();
        fakeContainer.bookService.modify(savedBookId, userRegisterRequest.getEmail(), bookModificationRequest);

//        검증
        Book book = fakeContainer.bookRepository.findById(savedBookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
        assertThat(book.getName()).isEqualTo("너에게 하고 싶은 말2");
        assertThat(book.getIsbn()).isEqualTo("10000");
    }

    @Test
    @DisplayName("유효하지 않은 도서 아이디에 대한 도서 수정 시 예외 발생")
    void modifyFailedByInvalidBookId() {
        FakeContainer fakeContainer = new FakeContainer();

//        유저 생성
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .email("a@a.com")
                .build();
        fakeContainer.userService.register(userRegisterRequest);

//        도서 등록
        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .name("너에게 하고 싶은 말")
                .isbn("9791191043235")
                .build();
        fakeContainer.bookService.register(userRegisterRequest.getEmail(), bookRegisterRequest);

//        도서 수정
        BookModificationRequest bookModificationRequest = BookModificationRequest.builder()
                .name("너에게 하고 싶은 말2")
                .isbn("10000")
                .build();
        assertThatThrownBy(() ->
                fakeContainer.bookService.modify(100L, userRegisterRequest.getEmail(), bookModificationRequest)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("권한이 없는 유저가 도서 수정 시 예외 발생")
    void modifyFailedByInvalidUser() {
        FakeContainer fakeContainer = new FakeContainer();

//        유저 생성
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .email("a@a.com")
                .build();
        fakeContainer.userService.register(userRegisterRequest);

        UserRegisterRequest userRegisterRequest2 = UserRegisterRequest.builder()
                .email("b@b.com")
                .build();
        fakeContainer.userService.register(userRegisterRequest2);

//        도서 등록
        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .name("너에게 하고 싶은 말")
                .isbn("9791191043235")
                .build();
        Long savedBookId = fakeContainer.bookService.register(userRegisterRequest.getEmail(), bookRegisterRequest);

//        도서 수정
        BookModificationRequest bookModificationRequest = BookModificationRequest.builder()
                .name("너에게 하고 싶은 말2")
                .isbn("10000")
                .build();
        assertThatThrownBy(() ->
                fakeContainer.bookService.modify(savedBookId, userRegisterRequest2.getEmail(), bookModificationRequest)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_FORBIDDEN.getMessage());
    }
}