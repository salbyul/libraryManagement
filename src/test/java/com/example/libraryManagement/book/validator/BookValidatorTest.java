package com.example.libraryManagement.book.validator;

import com.example.libraryManagement.book.domain.Book;
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

        fakeContainer.bookValidator.validateRegister(book);
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
                fakeContainer.bookValidator.validateRegister(nameNullBook)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_NAME_EMPTY.getMessage());

        assertThatThrownBy(() ->
                fakeContainer.bookValidator.validateRegister(emptyNameBook)
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
                fakeContainer.bookValidator.validateRegister(isbnNullBook)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_ISBN_EMPTY.getMessage());

        assertThatThrownBy(() ->
                fakeContainer.bookValidator.validateRegister(emptyIsbnBook)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_ISBN_EMPTY.getMessage());
    }

    @Test
    @DisplayName("ISBN 중복으로 등록 실패")
    void validateRegisterFailedByDuplicatedIsbn() {
        FakeContainer fakeContainer = new FakeContainer();

        User user = User.builder()
                .email("a@a.com")
                .bookList(new ArrayList<>())
                .build();

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
                fakeContainer.bookValidator.validateRegister(newBook)
        )
                .isInstanceOf(BookException.class)
                .hasMessage(BOOK_ISBN_DUPLICATION.getMessage());
    }
}