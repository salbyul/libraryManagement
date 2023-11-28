package com.example.libraryManagement.book.service;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.domain.BookState;
import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.dto.request.BookModificationRequest;
import com.example.libraryManagement.book.dto.request.BookRegisterRequest;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.book.service.port.BookRepository;
import com.example.libraryManagement.book.service.port.LentHistoryRepository;
import com.example.libraryManagement.book.validator.BookValidator;
import com.example.libraryManagement.common.response.error.ErrorType;
import com.example.libraryManagement.user.domain.User;
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
    private final LentHistoryRepository lentHistoryRepository;

    @Transactional
    public Long register(final String registrantEmail, final BookRegisterRequest bookRegisterRequest) {
        User registrant = getUserByEmail(registrantEmail);

        Book book = Book.builder()
                .name(bookRegisterRequest.getName())
                .isbn(bookRegisterRequest.getIsbn())
                .registrant(registrant)
                .state(BookState.LENDABLE)
                .build();
        bookValidator.validateForRegister(book);
        return bookRepository.save(book).getBookId();
    }

    private User getUserByEmail(final String registrantEmail) {
        return userRepository.findByEmail(registrantEmail)
                .orElseThrow(() -> new BookException(USER_NOT_FOUND));
    }

    @Transactional
    public void modify(final Long bookId, final String email, final BookModificationRequest bookModificationRequest) {
        Book book = getBookByBookId(bookId);
        User user = getUserByEmail(email);

        if (!book.isRightUser(user)) {
            throw new BookException(BOOK_FORBIDDEN);
        }
        book.update(bookModificationRequest.getName(), bookModificationRequest.getIsbn());
        bookValidator.validateForModification(book);
    }

    private Book getBookByBookId(final Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
    }

    @Transactional
    public void lend(final String email, final Long bookId) {
        User user = getUserByEmail(email);
        Book book = getBookByBookId(bookId);
        if (book.isLent()) {
            throw new BookException(ErrorType.BOOK_ALREADY_LENT);
        }
        book.lend();

        LentHistory lentHistory = LentHistory.builder()
                .user(user)
                .book(book)
                .build();
        lentHistoryRepository.save(lentHistory);
    }
}
