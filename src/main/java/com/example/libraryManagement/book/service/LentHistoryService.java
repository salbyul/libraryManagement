package com.example.libraryManagement.book.service;

import com.example.libraryManagement.book.domain.Book;
import com.example.libraryManagement.book.domain.LentHistory;
import com.example.libraryManagement.book.dto.response.LentHistoryResponse;
import com.example.libraryManagement.book.exception.BookException;
import com.example.libraryManagement.book.service.port.BookRepository;
import com.example.libraryManagement.book.service.port.LentHistoryRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.libraryManagement.common.response.error.ErrorType.*;

@Service
@Builder
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LentHistoryService {

    private final LentHistoryRepository lentHistoryRepository;
    private final BookRepository bookRepository;

    public List<LentHistoryResponse> getLentHistoryByBookId(final Long bookId) {
        getBookByBookId(bookId); // 해당 도서가 존재하는지 확인

        List<LentHistory> lentHistoryList = lentHistoryRepository.findByBookId(bookId);
        return lentHistoryList.stream()
                .map(LentHistoryResponse::translate)
                .collect(Collectors.toList());
    }

    private void getBookByBookId(final Long bookId) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
    }
}
