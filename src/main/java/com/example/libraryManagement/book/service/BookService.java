package com.example.libraryManagement.book.service;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.domain.BookState;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.book.service.port.BookRepository;
import com.example.libraryManagement.book.validator.BookValidator;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.exception.UserException;
import com.example.libraryManagement.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.libraryManagement.common.response.error.ErrorType.*;

@Service
@Builder
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    @Transactional
    public Long register(final BookRegisterRequest bookRegisterRequest) {
        User registrant = getUserByEmail(bookRegisterRequest.getRegistrantEmail());

        Book book = Book.builder()
                .name(bookRegisterRequest.getName())
                .isbn(bookRegisterRequest.getIsbn())
                .registrant(registrant)
                .state(BookState.LENDABLE)
                .build();
        bookValidator.validateRegister(book);
        return bookRepository.save(book).getBookId();
    }

    private User getUserByEmail(final String registrantEmail) {
        return userRepository.findByEmail(registrantEmail)
                .orElseThrow(() -> new BookException(USER_NOT_FOUND));
    }
}
