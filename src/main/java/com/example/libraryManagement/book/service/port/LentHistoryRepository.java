package com.example.libraryManagement.book.service.port;

import com.example.libraryManagement.book.domain.LentHistory;

import java.util.Optional;

public interface LentHistoryRepository {

    LentHistory save(final LentHistory lentHistory);

    Optional<LentHistory> findByLentHistoryId(final Long lentHistoryId);
}