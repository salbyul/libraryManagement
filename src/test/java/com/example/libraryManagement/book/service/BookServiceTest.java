package com.example.libraryManagement.book.service;

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
                .registrantEmail("a@a.com")
                .name("너에게 하고 싶은 말")
                .isbn("9791191043235")
                .build();

        fakeContainer.bookService.register(bookRegisterRequest);
    }

    @Test
    @DisplayName("도서 등록 시 존재하지 않는 유저로 인한 예외 발생")
    void registerFailedByNotExistUser() {
        FakeContainer fakeContainer = new FakeContainer();

        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .registrantEmail("a@a.com")
                .name("너에게 하고 싶은 말")
                .isbn("9791191043235")
                .build();

        assertThatThrownBy(() ->
                fakeContainer.bookService.register(bookRegisterRequest)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(USER_NOT_FOUND.getMessage());
    }
}