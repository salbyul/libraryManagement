package com.example.libraryManagement.book.controller;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.common.response.error.ErrorType;
import com.example.libraryManagement.mock.FakeContainer;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.dto.request.UserRegisterRequest;
import com.example.libraryManagement.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.libraryManagement.common.response.error.ErrorType.*;
import static org.assertj.core.api.Assertions.*;

class BookControllerTest {

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
        fakeContainer.bookController.register(bookRegisterRequest);
        User registrant = fakeContainer.userRepository.findByEmail("a@a.com")
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        Book book = fakeContainer.bookRepository.findByIsbn("9791191043235")
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
        assertThat(book.getName()).isEqualTo("너에게 하고 싶은 말");
        assertThat(book.getIsbn()).isEqualTo("9791191043235");
        assertThat((book.getRegistrant())).isEqualTo(registrant);
    }
}