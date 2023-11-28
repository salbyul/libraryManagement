package com.example.libraryManagement.book.controller;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.domain.BookState;
import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.common.response.ApiResponse;
import com.example.libraryManagement.mock.FakeContainer;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.dto.request.UserRegisterRequest;
import com.example.libraryManagement.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.libraryManagement.common.response.error.ErrorType.*;
import static org.assertj.core.api.Assertions.assertThat;

class LentHistoryControllerTest {

    @Test
    @DisplayName("도서 대출 성공")
    void lend() {
        FakeContainer fakeContainer = new FakeContainer();

//        유저 생성
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .email("a@a.com")
                .build();
        fakeContainer.userService.register(userRegisterRequest);

//        도서 생성
        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .name("너에게 하고 싶은 말")
                .isbn("9791191043235")
                .build();
        Long savedBookId = fakeContainer.bookService.register(userRegisterRequest.getEmail(), bookRegisterRequest);

//        도서 대출
        ApiResponse response = fakeContainer.lentHistoryController.lend(savedBookId, userRegisterRequest.getEmail());
        Long savedLentHistoryId = (Long) response.getBody().get("id");

//        검증
        LentHistory lentHistory = fakeContainer.lentHistoryRepository.findByLentHistoryId(savedLentHistoryId)
                .orElseThrow(() -> new BookException(LENT_HISTORY_NOT_FOUND));
        User user = fakeContainer.userRepository.findByEmail(userRegisterRequest.getEmail())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        Book book = fakeContainer.bookRepository.findById(savedBookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
        assertThat(lentHistory.getUser()).isEqualTo(user);
        assertThat(lentHistory.getBook()).isEqualTo(book);
        assertThat(book.getState()).isEqualTo(BookState.LENT);
    }
}