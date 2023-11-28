package com.example.libraryManagement.book.service;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.book.service.port.BookRepository;
import com.example.libraryManagement.book.service.port.LentHistoryRepository;
import com.example.libraryManagement.common.response.error.ErrorType;
import com.example.libraryManagement.user.domain.User;
import com.example.libraryManagement.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LentHistoryService {

    private final LentHistoryRepository lentHistoryRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Long lend(final String email, final Long bookId) {
        User user = getUserByEmail(email);
        Book book = getBookByBookId(bookId);

        LentHistory lentHistory = LentHistory.builder()
                .user(user)
                .book(book)
                .build();
        return lentHistoryRepository.save(lentHistory).getLentHistoryId();
    }

    private User getUserByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BookException(ErrorType.USER_NOT_FOUND));
    }

    private Book getBookByBookId(final Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException(ErrorType.BOOK_NOT_FOUND));
    }
}
