package com.example.libraryManagement.book.validator;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.book.service.port.BookRepository;
import com.example.libraryManagement.common.annotation.Validator;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.example.libraryManagement.common.response.error.ErrorType.*;

@Validator
@RequiredArgsConstructor
@Builder
public class BookValidator {

    private final BookRepository bookRepository;

    public void validateRegister(final Book book) {
        validateName(book.getName());
        validateIsbn(book.getIsbn());
    }

    private void validateName(final String name) {
        if (isEmpty(name)) {
            throw new BookException(BOOK_NAME_EMPTY);
        }
    }

    private void validateIsbn(final String isbn) {
        if (isEmpty(isbn)) {
            throw new BookException(BOOK_ISBN_EMPTY);
        }
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        if (optionalBook.isPresent()) {
            throw new BookException(BOOK_ISBN_DUPLICATION);
        }
    }

    private boolean isEmpty(final String value) {
        return !StringUtils.hasText(value);
    }
}
