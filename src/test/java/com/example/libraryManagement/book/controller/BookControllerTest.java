package com.example.libraryManagement.book.controller;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.domain.BookState;
import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.dto.request.BookModificationRequest;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.mock.FakeContainer;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.dto.request.UserRegisterRequest;
import com.example.libraryManagement.user.exception.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.libraryManagement.common.response.error.ErrorType.*;
import static org.assertj.core.api.Assertions.*;

class BookControllerTest {

    @Test
    @DisplayName("도서 등록 성공")
    void register() {
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
        fakeContainer.bookController.register(userRegisterRequest.getEmail(), bookRegisterRequest);
        User registrant = fakeContainer.userRepository.findByEmail("a@a.com")
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        Book book = fakeContainer.bookRepository.findByIsbn("9791191043235")
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
        assertThat(book.getName()).isEqualTo("너에게 하고 싶은 말");
        assertThat(book.getIsbn()).isEqualTo("9791191043235");
        assertThat((book.getRegistrant())).isEqualTo(registrant);
    }

    @Test
    @DisplayName("도서 수정 성공")
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
        fakeContainer.bookController.modify(savedBookId, bookModificationRequest, userRegisterRequest.getEmail());
    }

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
        fakeContainer.bookController.lend(savedBookId, userRegisterRequest.getEmail());

//        검증
        List<LentHistory> lentHistoryList = fakeContainer.lentHistoryRepository.findByBookId(savedBookId);
        LentHistory lentHistory = lentHistoryList.get(0);
        User user = fakeContainer.userRepository.findByEmail(userRegisterRequest.getEmail())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        Book book = fakeContainer.bookRepository.findById(savedBookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
        assertThat(lentHistory.getUser()).isEqualTo(user);
        assertThat(lentHistory.getBook()).isEqualTo(book);
        assertThat(book.getState()).isEqualTo(BookState.LENT);
    }

    @Test
    @DisplayName("도서 반납 성공")
    void returnBook() {
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
        fakeContainer.bookController.lend(savedBookId, userRegisterRequest.getEmail());

//        도서 반납
        fakeContainer.bookController.returnBook(savedBookId, userRegisterRequest.getEmail());

//        검증
        Book book = fakeContainer.bookRepository.findById(savedBookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
        LentHistory lentHistory = fakeContainer.lentHistoryRepository.findByBookId(savedBookId).get(0);

        assertThat(book.isLent()).isFalse();
        assertThat(lentHistory.getReturnDate()).isNotNull();
    }
}