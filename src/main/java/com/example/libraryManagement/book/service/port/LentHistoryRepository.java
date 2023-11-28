package com.example.libraryManagement.book.service.port;

import com.example.libraryManagement.book.domain.LentHistory;

import java.util.List;
import java.util.Optional;

public interface LentHistoryRepository {

    LentHistory save(final LentHistory lentHistory);

    List<LentHistory> findByBookId(final Long bookId);

    Optional<LentHistory> findNotReturnedByUserIdAndBookId(final Long userId, final Long bookId);
}
