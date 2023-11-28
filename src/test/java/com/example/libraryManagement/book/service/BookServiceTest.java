package com.example.libraryManagement.book.service;

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
        fakeContainer.bookService.lend(userRegisterRequest.getEmail(), savedBookId);

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
    @DisplayName("존재하지 않는 유저로 인한 도서 대출 실패")
    void lendFailedByNotExistUser() {
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

//        검증
        assertThatThrownBy(() ->
                fakeContainer.bookService.lend("asdfasdfasdf@asdfasdfasdf.com", savedBookId)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(USER_NOT_FOUND.getMessage());

    }

    @Test
    @DisplayName("존재하지 않는 도서로 인한 도서 대출 실패")
    void lendFailedByNotExistBook() {
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
        fakeContainer.bookService.register(userRegisterRequest.getEmail(), bookRegisterRequest);

//        검증
        assertThatThrownBy(() ->
                fakeContainer.bookService.lend(userRegisterRequest.getEmail(), -1L)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("이미 대출된 도서를 대출할 경우 예외 발생")
    void lendFailedByLentBook() {
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
        fakeContainer.bookController.lend(savedBookId, userRegisterRequest.getEmail());

//        검증
        assertThatThrownBy(() ->
                fakeContainer.bookService.lend(userRegisterRequest.getEmail(), savedBookId)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_ALREADY_LENT.getMessage());
    }
}