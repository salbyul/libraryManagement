package com.example.libraryManagement.book.validator;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.mock.FakeContainer;
import com.example.libraryManagement.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.example.libraryManagement.common.response.error.ErrorType.*;
import static org.assertj.core.api.Assertions.*;

class BookValidatorTest {

    @Test
    @DisplayName("도서 등록 검증 성공")
    void validateRegister() {
        FakeContainer fakeContainer = new FakeContainer();

        Book book = Book.builder()
                .name("너에게 하고 싶은 말")
                .isbn("10000")
                .build();

        fakeContainer.bookValidator.validateForRegister(book);
    }

    @Test
    @DisplayName("도서의 이름 값이 입력되지 않았을 경우 예외 발생")
    void validateFailedByEmptyName() {
        FakeContainer fakeContainer = new FakeContainer();

        Book nameNullBook = Book.builder()
                .isbn("10000")
                .build();
        Book emptyNameBook = Book.builder()
                .name("")
                .isbn("10000")
                .build();
        assertThatThrownBy(() ->
                fakeContainer.bookValidator.validateForRegister(nameNullBook)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_NAME_EMPTY.getMessage());

        assertThatThrownBy(() ->
                fakeContainer.bookValidator.validateForRegister(emptyNameBook)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_NAME_EMPTY.getMessage());
    }

    @Test
    @DisplayName("ISBN 값이 입력되지 않았을 경우 예외 발생")
    void validateRegisterFailedByEmptyIsbn() {
        FakeContainer fakeContainer = new FakeContainer();

        Book isbnNullBook = Book.builder()
                .name("너에게 하고 싶은 말")
                .build();
        Book emptyIsbnBook = Book.builder()
                .name("너에게 하고 싶은 말")
                .isbn("")
                .build();
        assertThatThrownBy(() ->
                fakeContainer.bookValidator.validateForRegister(isbnNullBook)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_ISBN_EMPTY.getMessage());

        assertThatThrownBy(() ->
                fakeContainer.bookValidator.validateForRegister(emptyIsbnBook)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_ISBN_EMPTY.getMessage());
    }

    @Test
    @DisplayName("ISBN 중복으로 등록 실패")
    void validateRegisterFailedByDuplicatedIsbn() {
        FakeContainer fakeContainer = new FakeContainer();

//        유저 생성
        User user = User.builder()
                .email("a@a.com")
                .bookList(new ArrayList<>())
                .build();

//        도서 생성
        Book exist = Book.builder()
                .name("너에게 하고 싶은 말")
                .isbn("10000")
                .registrant(user)
                .build();
        fakeContainer.bookRepository.save(exist);

        Book newBook = Book.builder()
                .name("너에게 하고 싶은 말")
                .isbn("10000")
                .registrant(user)
                .build();

        assertThatThrownBy(() ->
                fakeContainer.bookValidator.validateForRegister(newBook)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_ISBN_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("도서 수정 검증 성공")
    void validateModification() {
        FakeContainer fakeContainer = new FakeContainer();

//        유저 생성
        User user = User.builder()
                .email("a@a.com")
                .bookList(new ArrayList<>())
                .build();
        fakeContainer.userRepository.save(user);

//        도서 생성
        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .registrantEmail(user.getEmail())
                .name("너에게 하고 싶은 말")
                .isbn("100000")
                .build();
        Long savedBookId = fakeContainer.bookService.register(bookRegisterRequest);

//        도서 수정
        Book book = fakeContainer.bookRepository.findById(savedBookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
        book.update("너에게 하고 싶은 말2", "101");
        fakeContainer.bookValidator.validateForModification(book);
    }

    @Test
    @DisplayName("비어있는 ISBN으로 인한 도서 수정 실패")
    void validateModificationFailedByEmptyIsbn() {
        FakeContainer fakeContainer = new FakeContainer();

//        유저 생성
        User user = User.builder()
                .email("a@a.com")
                .bookList(new ArrayList<>())
                .build();
        fakeContainer.userRepository.save(user);

//        도서 등록
        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .registrantEmail(user.getEmail())
                .name("너에게 하고 싶은 말")
                .isbn("100000")
                .build();

        Long savedBookId = fakeContainer.bookService.register(bookRegisterRequest);

//        도서 수정
        Book book = fakeContainer.bookRepository.findById(savedBookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
        book.update("너에게 하고 싶은 말2", "");
        assertThatThrownBy(() ->
                fakeContainer.bookValidator.validateForModification(book)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_ISBN_EMPTY.getMessage());
    }

    @Test
    @DisplayName("ISBN 중복으로 인한 도서 수정 실패")
    void validateModificationFailedByDuplicatedIsbn() {
        FakeContainer fakeContainer = new FakeContainer();

//        유저 생성
        User user = User.builder()
                .email("a@a.com")
                .bookList(new ArrayList<>())
                .build();
        fakeContainer.userRepository.save(user);

//        도서 등록
        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .registrantEmail(user.getEmail())
                .name("너에게 하고 싶은 말")
                .isbn("100000")
                .build();

        BookRegisterRequest bookRegisterRequest2 = BookRegisterRequest.builder()
                .registrantEmail(user.getEmail())
                .name("너에게 하고 싶은 말")
                .isbn("100")
                .build();
        fakeContainer.bookService.register(bookRegisterRequest);
        Long savedBookId = fakeContainer.bookService.register(bookRegisterRequest2);

//        도서 수정
        Book book = fakeContainer.bookRepository.findById(savedBookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
        book.update("너에게 하고 싶은 말2", "100000");
        assertThatThrownBy(() ->
                fakeContainer.bookValidator.validateForModification(book)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_ISBN_DUPLICATION.getMessage());
    }
}